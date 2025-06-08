package com.sportygroup.tracker.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.tracker.domain.model.LiveSports;
import com.sportygroup.tracker.domain.ports.out.MessagePublisherPort;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * KafkaMessagePublisher is responsible for publishing messages to a Kafka topic.
 */
@Slf4j
@Component
public class KafkaMessagePublisher implements MessagePublisherPort {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessagePublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("${kafka.topic.name}") String topic;

    public KafkaMessagePublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(LiveSports response) {
        try {
            String message = objectMapper.writeValueAsString(response);
            kafkaTemplate.send(topic, response.eventId(), message)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Message sent successfully: {}", message);
                        } else {
                            log.error("Failed to send message: {}", ex.getMessage(), ex);
                        }
                    });
        } catch (JsonProcessingException e) {
            log.error("Error serializing ApiResponse: {}", e.getMessage(), e);
        }
    }
}
