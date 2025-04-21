package com.eloptimeraren.datanormalizer.service;

import com.eloptimeraren.datanormalizer.mapper.NormalizedEnergyPriceMapper;
import com.eloptimeraren.fetch_elpris.dto.ElprisElectricityPrice;
import com.eloptimeraren.fetch_mgrey.dto.MgreyElectricityPriceCollection;
import com.eloptimeraren.shared.enums.KafkaTopics;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyPriceConsumer {

    private static final Logger log = LoggerFactory.getLogger(EnergyPriceConsumer.class);
    private static final String GROUP_ID = "energy-price-consumer";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NormalizedElectricityPriceProducer producer;

    @KafkaListener(topics = "#{T(com.eloptimeraren.shared.enums.KafkaTopics).ELECTRICITY_PRICE_ELPRIS_TOPIC.value}",
            groupId = GROUP_ID)
    public void consume(String message) {
        try {
            List<ElprisElectricityPrice> elprisElectricityPrices = objectMapper.readValue(
                    message, new TypeReference<>() {
                    }
            );

            log.info("Consumed {} elpris energy price entities from topic {}", elprisElectricityPrices.size(),
                    KafkaTopics.ELECTRICITY_PRICE_ELPRIS_TOPIC.value);

            producer.sendNormalizedElectricityPrices(
                    NormalizedEnergyPriceMapper.mapToNormalizedLists(elprisElectricityPrices));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "#{T(com.eloptimeraren.shared.enums.KafkaTopics).ELECTRICITY_PRICE_MGREY_TOPIC.value}",
            groupId = GROUP_ID)
    public void consumeMgrey(String message) {
        try {
            MgreyElectricityPriceCollection mgreyElectricityPriceCollection = objectMapper.readValue(
                    message, MgreyElectricityPriceCollection.class);

            log.info("Consumed {} mgrey energy price entities from topic {}",
                    mgreyElectricityPriceCollection.getTotalSize(),
                    KafkaTopics.ELECTRICITY_PRICE_MGREY_TOPIC.value);

            NormalizedEnergyPriceMapper.mapToNormalizedLists(mgreyElectricityPriceCollection).forEach(
                    prices -> producer.sendNormalizedElectricityPrices(prices));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
