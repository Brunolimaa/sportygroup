package com.sportygroup.tracker.infrastructure.resilience;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.out.ExternalApiClientPort;
import com.sportygroup.tracker.domain.ports.out.ExternalResilientApiCallerPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ResilientApiCaller is responsible for making resilient calls to an external API.
 * It uses Resilience4j's CircuitBreaker and Retry annotations to handle failures gracefully.
 */
@Component
public class ResilientApiCaller implements ExternalResilientApiCallerPort {

    private static final Logger log = LoggerFactory.getLogger(ResilientApiCaller.class);
    private final ExternalApiClientPort apiPort;

    public ResilientApiCaller(ExternalApiClientPort apiPort) {
        this.apiPort = apiPort;
    }

    @Retry(name = "eventApiRetry")
    @CircuitBreaker(name = "eventApiRetry", fallbackMethod = "fallback")
    public LiveSports fetch(String eventId) {
        return apiPort.fetchEventData(eventId);
    }

    private LiveSports fallback(String eventId, Throwable t) {
        log.error("Fallback triggered for eventId {}: {}", eventId, t.getMessage());
        throw new RuntimeException("Failed to fetch event data for " + eventId, t);
    }
}
