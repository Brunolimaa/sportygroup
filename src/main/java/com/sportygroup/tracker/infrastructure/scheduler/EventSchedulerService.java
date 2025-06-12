package com.sportygroup.tracker.infrastructure.scheduler;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.in.EventSchedulerPort;
import com.sportygroup.tracker.domain.ports.out.ExternalResilientApiCallerPort;
import com.sportygroup.tracker.domain.ports.out.MessagePublisherPort;
import com.sportygroup.tracker.infrastructure.resilience.ResilientApiCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

/**
 * EventSchedulerService is responsible for scheduling tasks to track events.
 * It uses an ExternalApiClientPort to fetch event data and a MessagePublisherPort to publish the data.
 */
@Service
public class EventSchedulerService implements EventSchedulerPort {

    private static final Logger log = LoggerFactory.getLogger(EventSchedulerService.class);

    private final ExternalResilientApiCallerPort resilientApiCaller;
    private final MessagePublisherPort kafkaPort;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public EventSchedulerService(ResilientApiCaller resilientApiCaller, MessagePublisherPort kafkaPort) {
        this.resilientApiCaller = resilientApiCaller;
        this.kafkaPort = kafkaPort;
    }

    @Override
    public void startTracking(String eventId) {
        if (scheduledTasks.containsKey(eventId)) {
            log.warn("Tracking is already active for eventId: {}", eventId);
            return;
        }

        log.info("Starting tracking for eventId: {}", eventId);

        ScheduledFuture<?> future = executorService.scheduleAtFixedRate(() -> {

            this.executeTaskLiveSports(eventId);

        }, 0, 10, TimeUnit.SECONDS);

        scheduledTasks.put(eventId, future);
    }

    @Override
    public void executeTaskLiveSports(String eventId) {
        try {
            LiveSports response = resilientApiCaller.fetch(eventId);
            if (response != null) {
                kafkaPort.publish(response);
            }
        } catch (Exception e) {
            log.error("Error in scheduled task for eventId {}: {}", eventId, e.getMessage(), e);
        }
    }

    @Override
    public void stopTracking(String eventId) {
        ScheduledFuture<?> future = scheduledTasks.remove(eventId);
        if (future != null) {
            log.info("Stopping tracking for eventId: {}", eventId);
            future.cancel(true);
        } else {
            log.warn("No task found to stop with eventId: {}", eventId);
        }
    }

}
