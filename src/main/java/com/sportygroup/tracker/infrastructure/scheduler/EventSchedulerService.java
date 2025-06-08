package com.sportygroup.tracker.infrastructure.scheduler;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.in.EventSchedulerPort;
import com.sportygroup.tracker.domain.ports.out.ExternalApiClientPort;
import com.sportygroup.tracker.domain.ports.out.MessagePublisherPort;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

/**
 * EventSchedulerService is responsible for scheduling tasks to track events.
 * It uses an ExternalApiClientPort to fetch event data and a MessagePublisherPort to publish the data.
 */
@Slf4j
@Service
public class EventSchedulerService implements EventSchedulerPort {

    private static final Logger log = LoggerFactory.getLogger(EventSchedulerService.class);

    private final ExternalApiClientPort apiPort;
    private final MessagePublisherPort kafkaPort;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public EventSchedulerService(ExternalApiClientPort apiPort, MessagePublisherPort kafkaPort) {
        this.apiPort = apiPort;
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
            try {
                LiveSports response = apiPort.fetchEventData(eventId);
                kafkaPort.publish(response);
            } catch (Exception e) {
                log.error("Error in scheduled task for eventId {}: {}", eventId, e.getMessage(), e);
            }
        }, 0, 10, TimeUnit.SECONDS);

        scheduledTasks.put(eventId, future);
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
