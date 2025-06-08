package com.sportygroup.tracker.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.tracker.application.EventStatusService;
import com.sportygroup.tracker.domain.model.Event;
import com.sportygroup.tracker.infrastructure.web.controller.EventController;
import com.sportygroup.tracker.infrastructure.web.controller.map.EventMapper;
import com.sportygroup.tracker.infrastructure.web.controller.request.EventRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventStatusService eventStatusService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        EventStatusService eventStatusService() {
            return mock(EventStatusService.class);
        }
        @Bean
        EventMapper eventMapper() {
            return mock(EventMapper.class);
        }
    }

    @Test
    void updateEventStatus_ShouldReturnOk() throws Exception {
        EventRequestDTO request = new EventRequestDTO("event-1", "live");
        Event event = new Event("event-1", "live");
        when(eventMapper.toDomain(org.mockito.ArgumentMatchers.any(EventRequestDTO.class))).thenReturn(event);

        mockMvc.perform(post("/v1/events/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(eventMapper).toDomain(org.mockito.ArgumentMatchers.any(EventRequestDTO.class));
        verify(eventStatusService).updateEventStatus(event);
    }
}