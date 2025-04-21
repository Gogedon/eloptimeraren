package com.eloptimeraren.business_logic.service.kafka;

import com.eloptimeraren.business_logic.db.repository.EmissionRepository;
import com.eloptimeraren.business_logic.mapper.EmissionMapper;
import com.eloptimeraren.datanormalizer.dto.NormalizedEmission;
import com.eloptimeraren.shared.enums.KafkaTopics;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NormalizedEmissionConsumer {

    private static final Logger log = LoggerFactory.getLogger(NormalizedEmissionConsumer.class);
    private static final String GROUP_ID = "normalized-emission-consumer";

    private final EmissionRepository emissionRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "#{T(com.eloptimeraren.shared.enums.KafkaTopics).EMISSIONS_NORMALIZED_TOPIC.value}",
            groupId = GROUP_ID)
    public void consume(String message) {
        try {
            List<NormalizedEmission> normalizedEmissions = objectMapper.readValue(
                    message, new TypeReference<>() {
                    }
            );
            log.info("Consumed {} normalized emission entities from topic {}", normalizedEmissions.size(),
                    KafkaTopics.EMISSIONS_NORMALIZED_TOPIC.value);

            emissionRepository.saveAll(EmissionMapper.mapToEntityList(normalizedEmissions));
            log.info("Saved {} emission entities to database", normalizedEmissions.size());
        } catch (Exception e) {
            log.error("Problem occurred when mapping and saving emission entities: {}", e.getMessage());
        }
    }

}