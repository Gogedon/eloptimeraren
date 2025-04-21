package com.eloptimeraren.datanormalizer.service;

import com.eloptimeraren.datanormalizer.dto.NormalizedEmission;
import com.eloptimeraren.shared.enums.KafkaTopics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormalizedEmissionsProducer {

    private static final Logger log = LoggerFactory.getLogger(NormalizedEmissionsProducer.class);
    private final KafkaTemplate<String, List<NormalizedEmission>> kafkaTemplate;

    public NormalizedEmissionsProducer(KafkaTemplate<String, List<NormalizedEmission>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNormalizedEmissions(List<NormalizedEmission> normalizedEmissions) {
        log.info("Sending {} normalized emissions to Kafka...", normalizedEmissions.size());
        kafkaTemplate.send(KafkaTopics.EMISSIONS_NORMALIZED_TOPIC.value, normalizedEmissions);
    }
}
