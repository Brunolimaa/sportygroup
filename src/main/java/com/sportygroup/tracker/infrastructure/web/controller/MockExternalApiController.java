package com.sportygroup.tracker.infrastructure.web.controller;

import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.infrastructure.web.controller.map.EventMapper;
import com.sportygroup.tracker.infrastructure.web.controller.response.EventResponseDTO;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Mock controller for simulating an external API that provides live event data.
 */
@RestController
public class MockExternalApiController implements MockExternalApiControllerApi {

    private final EventMapper mapper;

    public MockExternalApiController(EventMapper mapper) {
        this.mapper = mapper;
    }

    public EventResponseDTO getEvent(String eventId) {
        String score = new Random().nextInt(5) + ":" + new Random().nextInt(5);
        return this.mapper.toTDO(new LiveSports(eventId, score));
    }
}