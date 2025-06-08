package com.sportygroup.tracker.domain.model;


/**
 * Represents the response from the external API containing event details.
 *
 * @param eventId The unique identifier of the event.
 * @param currentScore The current score of the event in a "teamA:teamB" format.
 */
public record LiveSports(String eventId, String currentScore) {

}
