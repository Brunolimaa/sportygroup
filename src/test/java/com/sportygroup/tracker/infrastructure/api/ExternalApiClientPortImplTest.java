package com.sportygroup.tracker.infrastructure.api;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.infrastructure.repository.ExternalApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExternalApiClientPortImplTest {

    private ExternalApiClient externalApiClient;
    private ExternalApiClientPortImpl externalApiClientPort;

    @BeforeEach
    void setUp() {
        externalApiClient = Mockito.mock(ExternalApiClient.class);
        externalApiClientPort = new ExternalApiClientPortImpl(externalApiClient);
    }

    @Test
    void fetchEventData_ShouldDelegateToExternalApiClient() {
        String eventId = "event-123";
        LiveSports expected = new LiveSports("eventId", "");
        Mockito.when(externalApiClient.fetchEventData(eventId)).thenReturn(expected);

        LiveSports result = externalApiClientPort.fetchEventData(eventId);

        assertEquals(expected, result);
        Mockito.verify(externalApiClient).fetchEventData(eventId);
    }
}