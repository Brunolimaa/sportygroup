package com.sportygroup.tracker.infrastructure.web.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for representing an event request.
 * This DTO contains the event ID and its current status.
 */
@Schema(description = "Data transfer object representing an event with its ID and status.")
public record EventRequestDTO(
        @Schema(description = "Unique identifier of the event", example = "12345")
        String eventId,

        @Schema(description = "Current status or score of the event", example = "2:1")
        String status
) {}