package com.sportygroup.tracker.domain.model;

/**
 * Represents an event with its ID and status.
 *
 * @param eventId The unique identifier of the event.
 * @param status The current status of the event (e.g., "live", "completed").
 */
public record Event(String eventId, String status) {

}