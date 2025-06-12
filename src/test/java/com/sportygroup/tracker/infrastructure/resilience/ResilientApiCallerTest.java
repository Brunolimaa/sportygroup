package com.sportygroup.tracker.infrastructure.resilience;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.out.ExternalApiClientPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ResilientApiCallerTest {

    @MockBean
    private ExternalApiClientPort apiPort;

    @Autowired
    private ResilientApiCaller resilientApiCaller;

    @Test
    void shouldRetryThreeTimesThenSucceed() {
        String eventId = "event-retry-id";
        LiveSports liveSports = new LiveSports(eventId, "1:0");

        when(apiPort.fetchEventData(eventId))
                .thenThrow(new RuntimeException("fail"))
                .thenThrow(new RuntimeException("fail"))
                .thenReturn(liveSports);

        LiveSports result = resilientApiCaller.fetch(eventId);

        assertEquals(liveSports, result);
        verify(apiPort, times(3)).fetchEventData(eventId);
    }
}