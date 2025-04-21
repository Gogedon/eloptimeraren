package com.eloptimeraren.fetch_mgrey.service;

import com.eloptimeraren.fetch_mgrey.dto.MgreyElectricityPriceCollection;
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

    private final KafkaTemplate<String, MgreyElectricityPriceCollection> kafkaTemplate;

    public PublisherService(KafkaTemplate<String, MgreyElectricityPriceCollection> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(MgreyElectricityPriceCollection prices) {
        logger.info("Publishing mgrey electricity prices to Kafka...");
        CompletableFuture<SendResult<String, MgreyElectricityPriceCollection>> future =
                kafkaTemplate.send(KafkaTopics.ELECTRICITY_PRICE_MGREY_TOPIC.value, prices);

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