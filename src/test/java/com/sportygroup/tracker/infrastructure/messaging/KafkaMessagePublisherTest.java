package com.sportygroup.tracker.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.tracker.domain.model.LiveSports;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

class KafkaMessagePublisherTest {

    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;
    private KafkaMessagePublisher publisher;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        objectMapper = mock(ObjectMapper.class);
        publisher = new KafkaMessagePublisher(kafkaTemplate, objectMapper);

        try {
            var field = KafkaMessagePublisher.class.getDeclaredField("topic");
            field.setAccessible(true);
            field.set(publisher, "test-topic");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void publish_ShouldSerializeAndSendMessage() throws Exception {
        LiveSports liveSports = new LiveSports("event-1", "2:1");
        String json = "{\"eventId\":\"event-1\",\"name\":\"2:1\"}";
        when(objectMapper.writeValueAsString(liveSports)).thenReturn(json);

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(future);

        publisher.publish(liveSports);

        verify(objectMapper).writeValueAsString(liveSports);
        verify(kafkaTemplate).send("test-topic", "event-1", json);
    }

    @Test
    void publish_ShouldLogErrorOnSerializationException() throws Exception {
        LiveSports liveSports = new LiveSports("event-2", "2:1");
        when(objectMapper.writeValueAsString(liveSports)).thenThrow(new JsonProcessingException("error") {});

        publisher.publish(liveSports);

        verify(objectMapper).writeValueAsString(liveSports);
        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    void publish_ShouldLogErrorOnSendException() throws Exception {
        LiveSports liveSports = new LiveSports("event-3", "2:2");
        String json = "{\"eventId\":\"event-3\",\"name\":\"2:2\"}";
        when(objectMapper.writeValueAsString(liveSports)).thenReturn(json);

        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(future);

        publisher.publish(liveSports);

        Exception sendException = new RuntimeException("Kafka send failed");
        future.completeExceptionally(sendException);

        verify(objectMapper).writeValueAsString(liveSports);
        verify(kafkaTemplate).send("test-topic", "event-3", json);
    }
}