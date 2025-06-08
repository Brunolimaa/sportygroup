package com.sportygroup.tracker.application;

import com.sportygroup.tracker.domain.model.Event;
import com.sportygroup.tracker.domain.ports.in.EventSchedulerPort;
import com.sportygroup.tracker.domain.ports.in.EventStatusUseCase;
import org.springframework.stereotype.Service;

/**
 * Service to manage the status of events.
 * It updates the event status and starts or stops tracking based on the status.
 */
@Service
public class EventStatusService implements EventStatusUseCase {

    private final EventSchedulerPort eventScheduler;

    public EventStatusService(EventSchedulerPort eventScheduler) {
        this.eventScheduler = eventScheduler;
    }

    @Override
    public void updateEventStatus(Event event) {
        if ("live".equalsIgnoreCase(event.status())) {
            eventScheduler.startTracking(event.eventId());
        } else {
            eventScheduler.stopTracking(event.eventId());
        }
    }

}
