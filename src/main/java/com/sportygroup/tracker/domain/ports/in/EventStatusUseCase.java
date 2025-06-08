package com.sportygroup.tracker.domain.ports.in;

import com.sportygroup.tracker.domain.model.Event;

/**
 * Use case interface for managing the status of events.
 * This interface defines methods to update the status of an event.
 */
public interface EventStatusUseCase {
    void updateEventStatus(Event event);
}