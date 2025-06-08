package com.sportygroup.tracker.infrastructure.web.controller;


import com.sportygroup.tracker.application.EventStatusService;
import com.sportygroup.tracker.infrastructure.web.controller.map.EventMapper;
import com.sportygroup.tracker.infrastructure.web.controller.request.EventRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing event-related operations.
 * This controller handles requests to update the status of events.
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
    public ResponseEntity<Void> manageEventLifecycle(EventRequestDTO request) {
        eventStatusService.updateEventStatus(this.eventMapper.toDomain(request));
        return ResponseEntity.ok().build();
    }

}
