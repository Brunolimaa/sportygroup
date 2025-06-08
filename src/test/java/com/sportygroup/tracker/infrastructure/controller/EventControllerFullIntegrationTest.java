package com.sportygroup.tracker.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.tracker.infrastructure.web.controller.request.EventRequestDTO;
import com.sportygroup.tracker.infrastructure.web.controller.request.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerFullIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateEventStatus_ShouldReturnOk() throws Exception {
        EventRequestDTO request = new EventRequestDTO("event-1", EventStatus.LIVE);

        mockMvc.perform(post("/v1/events/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}