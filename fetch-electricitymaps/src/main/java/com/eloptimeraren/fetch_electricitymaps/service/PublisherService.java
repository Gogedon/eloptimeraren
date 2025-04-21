package com.eloptimeraren.fetch_electricitymaps.service;

import com.eloptimeraren.fetch_electricitymaps.dto.ElectricitymapsEmissionCollection;
import com.eloptimeraren.shared.enums.KafkaTopics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

    private final KafkaTemplate<String, ElectricitymapsEmissionCollection> kafkaTemplate;

    public PublisherService(KafkaTemplate<String, ElectricitymapsEmissionCollection> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(ElectricitymapsEmissionCollection emissions) {
        logger.info("Publishing electricitymaps emissions to Kafka...");
        CompletableFuture<SendResult<String, ElectricitymapsEmissionCollection>> future =
                kafkaTemplate.send(KafkaTopics.EMISSIONS_ELECTRICITY_MAPS_TOPIC.value, emissions);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Message sent successfully to topic: {}, partition: {}, offset: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                logger.error("Failed to send message", ex);
            }
        });
    }

}