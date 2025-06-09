package com.sportygroup.tracker.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.tracker.domain.model.LiveSports;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.*;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class KafkaMessagePublisherIntegrationTest {

    static KafkaContainer kafka;
    static String topic = "test-topic";
    KafkaTemplate<String, String> kafkaTemplate;
    KafkaMessagePublisher publisher;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void startKafka() {
        kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));
        kafka.start();
    }

    @AfterAll
    static void stopKafka() {
        kafka.stop();
    }

    @BeforeEach
    void setUp() {
        var producerProps = new java.util.HashMap<String, Object>();
        producerProps.put("bootstrap.servers", kafka.getBootstrapServers());
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProps));
        publisher = new KafkaMessagePublisher(kafkaTemplate, objectMapper);

        // Set topic field via reflection
        try {
            var field = KafkaMessagePublisher.class.getDeclaredField("topic");
            field.setAccessible(true);
            field.set(publisher, topic);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void publish_ShouldSendMessageToKafka() {
        LiveSports liveSports = new LiveSports("event-1", "2:1");
        publisher.publish(liveSports);

        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps)) {
            consumer.subscribe(Collections.singleton(topic));
            var records = consumer.poll(Duration.ofSeconds(5));
            assertThat(records.count()).isGreaterThan(0);
            var record = records.iterator().next();
            assertThat(record.key()).isEqualTo("event-1");
            assertThat(record.value()).contains("2:1");
        }
    }
}
