package com.eloptimeraren.datanormalizer.service;

import com.eloptimeraren.datanormalizer.dto.NormalizedElectricityPrice;
import com.eloptimeraren.shared.enums.KafkaTopics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormalizedElectricityPriceProducer {

    private static final Logger log = LoggerFactory.getLogger(NormalizedElectricityPriceProducer.class);
    private final KafkaTemplate<String, List<NormalizedElectricityPrice>> kafkaTemplate;

    public NormalizedElectricityPriceProducer(KafkaTemplate<String, List<NormalizedElectricityPrice>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNormalizedElectricityPrices(List<NormalizedElectricityPrice> normalizedElectricityPrices) {
        log.info("Sending {} normalized energy prices to Kafka...", normalizedElectricityPrices.size());
        kafkaTemplate.send(KafkaTopics.ELECTRICITY_PRICE_NORMALIZED_TOPIC.value, normalizedElectricityPrices);
    }
}
