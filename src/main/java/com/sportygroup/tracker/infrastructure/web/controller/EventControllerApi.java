package com.sportygroup.tracker.infrastructure.web.controller;

import com.sportygroup.tracker.infrastructure.web.controller.request.EventRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * API interface for handling event-related operations.
 * This interface defines the endpoints for updating event status.
 */
@RequestMapping("/v1/events")
public interface EventControllerApi {

    @Operation(
            summary = "Update event status",
            description = "Updates the status of an event based on the provided request data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status updated successfully")
            }
    )
    @PostMapping("/status")
    ResponseEntity<Void> updateEventStatus(@RequestBody EventRequestDTO request);
}