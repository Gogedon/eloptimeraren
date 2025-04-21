package com.eloptimeraren.business_logic.service.kafka;

import com.eloptimeraren.business_logic.db.repository.ElectricityPriceRepository;
import com.eloptimeraren.business_logic.mapper.ElectricityPriceMapper;
import com.eloptimeraren.datanormalizer.dto.NormalizedElectricityPrice;
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
public class NormalizedEnergyPriceConsumer {

    private static final Logger log = LoggerFactory.getLogger(NormalizedEnergyPriceConsumer.class);
    private static final String GROUP_ID = "normalized-energy-price-consumer";

    private final ElectricityPriceRepository repository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "#{T(com.eloptimeraren.shared.enums.KafkaTopics).ELECTRICITY_PRICE_NORMALIZED_TOPIC.value}",
            groupId = GROUP_ID)
    public void consume(String message) {
        try {
            List<NormalizedElectricityPrice> normalizedElectricityPrices = objectMapper.readValue(
                    message, new TypeReference<>() {
                    }
            );
            log.info("Consumed {} normalized energy price entities from topic {}", normalizedElectricityPrices.size(),
                    KafkaTopics.ELECTRICITY_PRICE_NORMALIZED_TOPIC.value);

            repository.saveAll(ElectricityPriceMapper.mapToEntityList(normalizedElectricityPrices));
            log.info("Saved {} energy price entities to database", normalizedElectricityPrices.size());
        } catch (Exception e) {
            log.error("Problem occurred when mapping and saving electricity price entities: {}", e.getMessage());
        }
    }
}
