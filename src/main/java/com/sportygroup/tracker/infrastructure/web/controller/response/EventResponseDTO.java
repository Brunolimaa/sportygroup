package com.sportygroup.tracker.infrastructure.web.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response Data Transfer Object (DTO) for representing an event with its ID and current score.
 * This DTO is used to encapsulate the response data for an event.
 */
@Schema(description = "Response DTO containing event ID and its current score.")
public record EventResponseDTO(
        @Schema(description = "Unique identifier of the event", example = "12345")
        String eventId,

        @Schema(description = "Current score of the event", example = "2:1")
        String currentScore
) {}