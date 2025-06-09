package com.sportygroup.tracker.application;

import com.sportygroup.tracker.domain.model.Event;
import com.sportygroup.tracker.domain.ports.in.EventSchedulerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventStatusServiceTest {

    private EventSchedulerPort eventScheduler;
    private EventStatusService eventStatusService;

    @BeforeEach
    void setUp() {
        eventScheduler = Mockito.mock(EventSchedulerPort.class);
        eventStatusService = new EventStatusService(eventScheduler);
    }

    @Test
    void shouldStartTrackingWhenEventIsLive() {
        Event event = new Event("event-1", "live");
        eventStatusService.updateEventStatus(event);

        Mockito.verify(eventScheduler).startTracking("event-1");
        Mockito.verify(eventScheduler, Mockito.never()).stopTracking(Mockito.anyString());
    }

    @Test
    void shouldStopTrackingWhenEventIsNotLive() {
        Event event = new Event("event-2", "not_live");
        eventStatusService.updateEventStatus(event);

        Mockito.verify(eventScheduler).stopTracking("event-2");
        Mockito.verify(eventScheduler, Mockito.never()).startTracking(Mockito.anyString());
    }
}