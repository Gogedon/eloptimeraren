package com.eloptimeraren.business_logic.mapper;

import com.eloptimeraren.business_logic.api.request.ElectricityConsumption;
import com.eloptimeraren.business_logic.domain.ElectricityDataPoint;
import com.eloptimeraren.business_logic.domain.ElectricityDataPointBackground;
import com.eloptimeraren.business_logic.record.ElectricityPriceAverageResult;
import com.eloptimeraren.business_logic.record.EmissionAverageResult;

public class ElectricityDataPointMapper {


    public static ElectricityDataPoint mapToDataPoint(ElectricityConsumption consumption,
                                                      ElectricityPriceAverageResult priceMatch,
                                                      EmissionAverageResult emissionMatch) {
        var builder = ElectricityDataPoint.builder();

        if (priceMatch != null) {
            builder.costSek(consumption.getKiloWatt() * priceMatch.avgSekPerKWh());
            builder.costEur(consumption.getKiloWatt() * priceMatch.avgEurPerKWh());
        }

        if (emissionMatch != null) {
            builder.carbonEmissionGrams(consumption.getKiloWatt() * emissionMatch.avgGCO2ePerKWh());
        }

        return builder
                .timeStart(consumption.getTimeStart())
                .timeEnd(consumption.getTimeEnd())
                .kiloWatt(consumption.getKiloWatt())
                .background(mapToDataPointBackground(priceMatch, emissionMatch))
                .build();
    
    }

    private static ElectricityDataPointBackground mapToDataPointBackground(ElectricityPriceAverageResult priceMatch,
                                                                           EmissionAverageResult emissionMatch) {
        var builder = ElectricityDataPointBackground.builder();

        if (priceMatch != null) {
            builder.eurPerKWh(priceMatch.avgEurPerKWh());
            builder.sekPerKWh(priceMatch.avgSekPerKWh());
        }

        if (emissionMatch != null) {
            builder.carbonEmissionGramsPerKWh(emissionMatch.avgGCO2ePerKWh());
        }

        return builder.build();
    }
}
