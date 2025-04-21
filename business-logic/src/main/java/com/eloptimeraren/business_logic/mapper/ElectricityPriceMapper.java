package com.eloptimeraren.business_logic.mapper;

import com.eloptimeraren.business_logic.db.entity.ElectricityPriceEntity;
import com.eloptimeraren.datanormalizer.dto.NormalizedElectricityPrice;

import java.util.List;

public class ElectricityPriceMapper {

    public static List<ElectricityPriceEntity> mapToEntityList(
            List<NormalizedElectricityPrice> normalizedElectricityPrices) {
        return normalizedElectricityPrices.stream().map(ElectricityPriceMapper::mapToEntity).toList();
    }

    private static ElectricityPriceEntity mapToEntity(NormalizedElectricityPrice normalizedElectricityPrice) {
        return ElectricityPriceEntity.builder()
                .source(normalizedElectricityPrice.getSource())
                .sekPerKWh(normalizedElectricityPrice.getSekPerKWh())
                .eurPerKWh(normalizedElectricityPrice.getEurPerKWh())
                .exchangeRate(normalizedElectricityPrice.getExr())
                .priceZone(normalizedElectricityPrice.getPriceZone())
                .timeStart(normalizedElectricityPrice.getTimeStart())
                .timeEnd(normalizedElectricityPrice.getTimeEnd())
                .normalizedAt(normalizedElectricityPrice.getNormalizedAt())
                .build();
    }

}
