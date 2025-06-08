package com.sportygroup.tracker.domain.ports.in;

/**
 * Port for scheduling events.
 * This interface defines methods to start and stop tracking events based on their IDs.
 */
public interface EventSchedulerPort {
    void startTracking(String eventId);
    void stopTracking(String eventId);
}