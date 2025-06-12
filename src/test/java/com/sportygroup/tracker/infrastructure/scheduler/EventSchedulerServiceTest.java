package com.sportygroup.tracker.infrastructure.scheduler;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.out.ExternalApiClientPort;
import com.sportygroup.tracker.domain.ports.out.MessagePublisherPort;
import com.sportygroup.tracker.infrastructure.resilience.ResilientApiCaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class EventSchedulerServiceTest {

    private ExternalApiClientPort apiPort;
    private MessagePublisherPort kafkaPort;
    private ResilientApiCaller resilientApiCaller;
    private EventSchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        apiPort = mock(ExternalApiClientPort.class);
        kafkaPort = mock(MessagePublisherPort.class);
        resilientApiCaller = new ResilientApiCaller(apiPort);
        schedulerService = new EventSchedulerService(resilientApiCaller, kafkaPort);
    }

    @Test
    void startTracking_ShouldScheduleTaskAndPublish() throws InterruptedException {
        String eventId = "event-1";
        LiveSports liveSports = new LiveSports(eventId, "2:1");

        CountDownLatch latch = new CountDownLatch(1);
        when(apiPort.fetchEventData(eventId)).thenAnswer(invocation -> {
            latch.countDown();
            return liveSports;
        });

        schedulerService.startTracking(eventId);

        boolean completed = latch.await(3, TimeUnit.SECONDS);

        verify(apiPort, atLeastOnce()).fetchEventData(eventId);
        verify(kafkaPort, atLeastOnce()).publish(liveSports);
        schedulerService.stopTracking(eventId);

        assert completed : "Scheduled task did not execute in time.";
    }

    @Test
    void stopTracking_ShouldCancelScheduledTask() {
        String eventId = "event-2";
        schedulerService.startTracking(eventId);
        schedulerService.stopTracking(eventId);

        schedulerService.stopTracking(eventId);
    }
}