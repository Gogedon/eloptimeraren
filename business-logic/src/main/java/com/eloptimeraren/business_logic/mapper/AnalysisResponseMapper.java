package com.eloptimeraren.business_logic.mapper;

import com.eloptimeraren.business_logic.api.response.AnalysisResponse;
import com.eloptimeraren.business_logic.api.response.DataPointBackgroundResponse;
import com.eloptimeraren.business_logic.api.response.DataPointResponse;
import com.eloptimeraren.business_logic.domain.ElectricityDataPoint;
import com.eloptimeraren.business_logic.domain.ElectricityDataPointBackground;
import com.eloptimeraren.shared.enums.PriceZone;

import java.util.Comparator;
import java.util.List;

public class AnalysisResponseMapper {

    public static final String HOURLY = "hourly";

    public static AnalysisResponse mapToResponse(PriceZone priceZone, List<ElectricityDataPoint> dataPoints) {
        var dataPointResponses = mapToDataPointResponses(dataPoints);

        var mostExpensiveDP = dataPointResponses.stream()
                .filter(dp -> dp.getCostSek() != null)
                .max(Comparator.comparing(DataPointResponse::getCostSek))
                .orElse(null);
        var highestEmissionDP = dataPointResponses.stream()
                .filter(dp -> dp.getCarbonEmissionGrams() != null)
                .max(Comparator.comparing(DataPointResponse::getCarbonEmissionGrams))
                .orElse(null);

        return AnalysisResponse.builder()
                .dataPoints(dataPointResponses)
                .aggregationPeriod(HOURLY) // We only do hourly for this demo
                .highestEmissionDataPoint(highestEmissionDP)
                .mostExpensiveDataPoint(mostExpensiveDP)
                .priceZone(priceZone)
                .build();
    }

    private static List<DataPointResponse> mapToDataPointResponses(List<ElectricityDataPoint> dataPoints) {
        return dataPoints.stream().map(AnalysisResponseMapper::mapToDataPointResponse).toList();
    }

    private static DataPointResponse mapToDataPointResponse(ElectricityDataPoint dp) {
        return DataPointResponse.builder()
                .timeStart(dp.getTimeStart())
                .timeEnd(dp.getTimeEnd())
                .costSek(dp.getCostSek())
                .costEur(dp.getCostEur())
                .kiloWatt(dp.getKiloWatt())
                .carbonEmissionGrams(dp.getCarbonEmissionGrams())
                .background(mapToDataPointBackgroundResponse(dp.getBackground()))
                .build();
    }

    private static DataPointBackgroundResponse
        mapToDataPointBackgroundResponse(ElectricityDataPointBackground background) {
        return DataPointBackgroundResponse.builder()
                .carbonEmissionGramsPerKWh(background.getCarbonEmissionGramsPerKWh())
                .eurPerKWh(background.getEurPerKWh())
                .sekPerKWh(background.getSekPerKWh())
                .build();
    }


}
