package com.sportygroup.tracker.infrastructure.scheduler;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.out.ExternalApiClientPort;
import com.sportygroup.tracker.domain.ports.out.MessagePublisherPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EventSchedulerServiceTest {

    private ExternalApiClientPort apiPort;
    private MessagePublisherPort kafkaPort;
    private EventSchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        apiPort = mock(ExternalApiClientPort.class);
        kafkaPort = mock(MessagePublisherPort.class);
        schedulerService = new EventSchedulerService(apiPort, kafkaPort);
    }

    @Test
    void startTracking_ShouldScheduleTaskAndPublish() throws Exception {
        String eventId = "event-1";
        LiveSports liveSports = new LiveSports(eventId, "2:1");
        when(apiPort.fetchEventData(eventId)).thenReturn(liveSports);

        schedulerService.startTracking(eventId);

        Thread.sleep(100);

        verify(apiPort, atLeastOnce()).fetchEventData(eventId);
        verify(kafkaPort, atLeastOnce()).publish(liveSports);

        schedulerService.stopTracking(eventId);
    }

    @Test
    void stopTracking_ShouldCancelScheduledTask() {
        String eventId = "event-2";
        schedulerService.startTracking(eventId);

        schedulerService.stopTracking(eventId);

        schedulerService.stopTracking(eventId);
    }
}