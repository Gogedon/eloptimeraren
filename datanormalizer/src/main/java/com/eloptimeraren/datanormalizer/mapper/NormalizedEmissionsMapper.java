package com.eloptimeraren.datanormalizer.mapper;

import com.eloptimeraren.datanormalizer.dto.NormalizedEmission;
import com.eloptimeraren.fetch_electricitymaps.dto.ElectricitymapsEmission;
import com.eloptimeraren.fetch_electricitymaps.dto.ElectricitymapsEmissionCollection;
import com.eloptimeraren.shared.enums.KafkaTopics;
import com.eloptimeraren.shared.enums.PriceZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.List;

public class NormalizedEmissionsMapper {

    private static final Logger log = LoggerFactory.getLogger(NormalizedEmissionsMapper.class);

    public static List<NormalizedEmission> mapToNormalizedList(ElectricitymapsEmissionCollection emissions) {
        PriceZone priceZone;
        try {
            priceZone = PriceZone.valueOf(emissions.getZone().split("-")[1]);
        } catch (IllegalArgumentException e) {
            log.error("Could map price zone from {} to PriceZone enum. Skipping...", emissions.getZone());
            return List.of();
        }

        return emissions.getElectricitymapsEmissionList().stream().map(e -> mapToNormalized(e, priceZone)).toList();
    }

    private static NormalizedEmission mapToNormalized(ElectricitymapsEmission e, PriceZone priceZone) {
        ZonedDateTime timeStart = ZonedDateTime.parse(e.getDatetime());
        ZonedDateTime timeEnd = timeStart.plusHours(1);

        return NormalizedEmission.builder()
                .normalizedAt(ZonedDateTime.now())
                .priceZone(priceZone)
                .source(KafkaTopics.EMISSIONS_ELECTRICITY_MAPS_TOPIC.value)
                .gCO2ePerKWh(e.getCarbonIntensity())
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .build();
    }
    
}
