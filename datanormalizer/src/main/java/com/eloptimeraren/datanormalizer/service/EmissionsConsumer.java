package com.eloptimeraren.datanormalizer.service;

import com.eloptimeraren.datanormalizer.mapper.NormalizedEmissionsMapper;
import com.eloptimeraren.fetch_electricitymaps.dto.ElectricitymapsEmissionCollection;
import com.eloptimeraren.shared.enums.KafkaTopics;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmissionsConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmissionsConsumer.class);
    private static final String GROUP_ID = "energy-price-consumer";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NormalizedEmissionsProducer producer;

    @KafkaListener(topics = "#{T(com.eloptimeraren.shared.enums.KafkaTopics).EMISSIONS_ELECTRICITY_MAPS_TOPIC.value}",
            groupId = GROUP_ID)
    public void consume(String message) {
        try {
            ElectricitymapsEmissionCollection emissions = objectMapper.readValue(
                    message, new TypeReference<>() {
                    }
            );
            log.info("Consumed {} electricitymaps emission entities from topic {}",
                    emissions.getElectricitymapsEmissionList().size(),
                    KafkaTopics.EMISSIONS_ELECTRICITY_MAPS_TOPIC.value);
            producer.sendNormalizedEmissions(NormalizedEmissionsMapper.mapToNormalizedList(emissions));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
