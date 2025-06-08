package com.sportygroup.tracker.infrastructure.web.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Possible statuses or scores for an event.")
public enum EventStatus {
    @Schema(description = "Event is currently live.")
    LIVE,
    @Schema(description = "Event is not live or has ended.")
    NOT_LIVE,
}