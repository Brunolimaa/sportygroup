package com.sportygroup.tracker.infrastructure.web.controller;

import com.sportygroup.tracker.infrastructure.web.controller.response.EventResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Mock controller for simulating an external API that provides live event data.
 */
@RequestMapping("/v1/api")
public interface MockExternalApiControllerApi {

    @Operation(summary = "Get event by ID", description = "Returns a mock event with a random score")
    @GetMapping("/live/{eventId}")
    EventResponseDTO getEvent(
            @Parameter(description = "ID of the event") @PathVariable String eventId
    );

}
