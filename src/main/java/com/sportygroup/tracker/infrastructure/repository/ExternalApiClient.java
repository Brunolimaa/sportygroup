package com.sportygroup.tracker.infrastructure.repository;

import com.sportygroup.tracker.domain.model.LiveSports;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for interacting with an external API to fetch event data.
 * This interface defines the endpoint to retrieve live event data.
 */
@FeignClient(name = "external-api", url = "http://localhost:8085/v1/api")
public interface ExternalApiClient {

    @GetMapping("/live/{eventId}")
    LiveSports fetchEventData(@PathVariable("eventId") String eventId);
}