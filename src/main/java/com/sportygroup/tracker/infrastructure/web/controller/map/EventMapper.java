package com.sportygroup.tracker.infrastructure.web.controller.map;


import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.model.Event;
import com.sportygroup.tracker.infrastructure.web.controller.request.EventRequestDTO;
import com.sportygroup.tracker.infrastructure.web.controller.response.EventResponseDTO;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between EventRequestDTO, EventResponseDTO, and Event domain model.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toDomain(EventRequestDTO request);

    EventResponseDTO toTDO(LiveSports eventDomain);
}
