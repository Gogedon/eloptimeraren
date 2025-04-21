package com.eloptimeraren.business_logic.mapper;

import com.eloptimeraren.business_logic.db.entity.EmissionEntity;
import com.eloptimeraren.datanormalizer.dto.NormalizedEmission;

import java.util.List;

public class EmissionMapper {
    public static List<EmissionEntity> mapToEntityList(List<NormalizedEmission> normalizedEmissions) {
        return normalizedEmissions.stream().map(EmissionMapper::mapToEntity).toList();
    }

    private static EmissionEntity mapToEntity(NormalizedEmission normalizedEmission) {
        return EmissionEntity.builder()
                .source(normalizedEmission.getSource())
                .gCO2ePerKWh(normalizedEmission.getGCO2ePerKWh())
                .priceZone(normalizedEmission.getPriceZone())
                .timeStart(normalizedEmission.getTimeStart())
                .timeEnd(normalizedEmission.getTimeEnd())
                .normalizedAt(normalizedEmission.getNormalizedAt())
                .build();
    }
}
