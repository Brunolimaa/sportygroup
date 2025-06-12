package com.sportygroup.tracker.infrastructure.scheduler;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.out.ExternalApiClientPort;
import com.sportygroup.tracker.domain.ports.out.MessagePublisherPort;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class EventSchedulerServiceIntegrationTest {

    @MockBean
    private ExternalApiClientPort apiPort;

    @MockBean
    private MessagePublisherPort kafkaPort;

    @Autowired
    private EventSchedulerService schedulerService;

    @Test
    void executeTask_ShouldRetryAndEventuallyPublish() {
        String eventId = "event-retry-id";
        LiveSports liveSports = new LiveSports(eventId, "1:0");

        when(apiPort.fetchEventData(eventId))
                .thenThrow(new RuntimeException("API failure 1"))
                .thenThrow(new RuntimeException("API failure 2"))
                .thenReturn(liveSports);

        schedulerService.executeTaskLiveSports(eventId);

        verify(apiPort, times(3)).fetchEventData(eventId);
        verify(kafkaPort).publish(liveSports);
    }
}
