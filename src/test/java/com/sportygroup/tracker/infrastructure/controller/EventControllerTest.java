package com.sportygroup.tracker.infrastructure.controller;

import com.sportygroup.tracker.application.EventStatusService;
import com.sportygroup.tracker.domain.model.Event;
import com.sportygroup.tracker.infrastructure.web.controller.EventController;
import com.sportygroup.tracker.infrastructure.web.controller.map.EventMapper;
import com.sportygroup.tracker.infrastructure.web.controller.request.EventRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventControllerTest {

    private EventStatusService eventStatusService;
    private EventMapper eventMapper;
    private EventController controller;

    @BeforeEach
    void setUp() {
        eventStatusService = mock(EventStatusService.class);
        eventMapper = mock(EventMapper.class);
        controller = new EventController(eventStatusService, eventMapper);
    }

    @Test
    void updateEventStatus_ShouldDelegateToServiceAndReturnOk() {
        EventRequestDTO request = new EventRequestDTO("event-1", "Football Match");
        Event event = new Event("event-1", "Football Match");
        when(eventMapper.toDomain(request)).thenReturn(event);

        ResponseEntity<Void> response = controller.updateEventStatus(request);

        verify(eventMapper).toDomain(request);
        verify(eventStatusService).updateEventStatus(event);
        assertEquals(200, response.getStatusCode().value());
    }
}