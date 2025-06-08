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

        @Schema(
                description = "Current status of the event. Allowed values: LIVE (Event is currently live), NOT_LIVE (Event is not live or has ended).",
                example = "LIVE"
        )
        EventStatus status
) {}