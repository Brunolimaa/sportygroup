package com.sportygroup.tracker.infrastructure.controller.map;

import com.sportygroup.tracker.domain.model.Event;
import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.infrastructure.web.controller.map.EventMapper;
import com.sportygroup.tracker.infrastructure.web.controller.request.EventRequestDTO;
import com.sportygroup.tracker.infrastructure.web.controller.response.EventResponseDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperTest {

    private final EventMapper mapper = Mappers.getMapper(EventMapper.class);

    @Test
    void toDomain_ShouldMapRequestToDomain() {
        EventRequestDTO request = new EventRequestDTO("event-1", "Football Match");
        Event event = mapper.toDomain(request);

        assertNotNull(event);
        assertEquals(request.eventId(), event.eventId());
        assertEquals(request.status(), event.status());
    }

    @Test
    void toTDO_ShouldMapDomainToResponse() {
        LiveSports liveSports = new LiveSports("event-2", "Basketball Match");
        EventResponseDTO response = mapper.toTDO(liveSports);

        assertNotNull(response);
        assertEquals(liveSports.eventId(), response.eventId());
        assertEquals(liveSports.currentScore(), response.currentScore());
    }
}