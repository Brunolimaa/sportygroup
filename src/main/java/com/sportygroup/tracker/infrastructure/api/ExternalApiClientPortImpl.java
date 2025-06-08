package com.sportygroup.tracker.infrastructure.api;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.out.ExternalApiClientPort;
import com.sportygroup.tracker.infrastructure.repository.ExternalApiClient;
import org.springframework.stereotype.Component;

/**
 * Implementation of the ExternalApiClientPort interface.
 * This class uses an ExternalApiClient to fetch event data from an external API.
 */
@Component
public class ExternalApiClientPortImpl implements ExternalApiClientPort {

    private final ExternalApiClient externalApiClient;

    public ExternalApiClientPortImpl(ExternalApiClient externalApiClient) {
        this.externalApiClient = externalApiClient;
    }

    @Override
    public LiveSports fetchEventData(String eventId) {
        return externalApiClient.fetchEventData(eventId);
    }
}
