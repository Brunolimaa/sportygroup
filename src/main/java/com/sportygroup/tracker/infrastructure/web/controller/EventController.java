package com.sportygroup.tracker.infrastructure.web.controller;


import com.sportygroup.tracker.application.EventStatusService;
import com.sportygroup.tracker.infrastructure.web.controller.map.EventMapper;
import com.sportygroup.tracker.infrastructure.web.controller.request.EventRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling event status updates.
 * This controller provides an endpoint to update the status of an event.
 */
@RestController
public class EventController implements EventControllerApi {

    private final EventStatusService eventStatusService;
    private final EventMapper eventMapper;

    public EventController(EventStatusService eventStatusService, EventMapper eventMapper) {
        this.eventStatusService = eventStatusService;
        this.eventMapper = eventMapper;
    }

    @Override
    public ResponseEntity<Void> updateEventStatus(EventRequestDTO request) {
        eventStatusService.updateEventStatus(this.eventMapper.toDomain(request));
        return ResponseEntity.ok().build();
    }

}
