package com.eloptimeraren.business_logic.service;

import com.eloptimeraren.business_logic.api.request.ElectricityConsumption;
import com.eloptimeraren.business_logic.db.entity.ElectricityPriceEntity;
import com.eloptimeraren.business_logic.db.entity.EmissionEntity;
import com.eloptimeraren.business_logic.db.repository.ElectricityPriceRepository;
import com.eloptimeraren.business_logic.db.repository.EmissionRepository;
import com.eloptimeraren.business_logic.domain.ElectricityDataPoint;
import com.eloptimeraren.business_logic.mapper.ElectricityDataPointMapper;
import com.eloptimeraren.business_logic.record.ElectricityPriceAverageResult;
import com.eloptimeraren.business_logic.record.EmissionAverageResult;
import com.eloptimeraren.shared.enums.PriceZone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final EmissionRepository emissionRepository;
    private final ElectricityPriceRepository electricityPriceRepository;

    public List<ElectricityDataPoint> analyze(PriceZone priceZone, List<ElectricityConsumption> consumption) {
        var startTimes = consumption.stream().map(ElectricityConsumption::getTimeStart).collect(Collectors.toSet());
        var emissions = emissionRepository.findByPriceZoneAndStartTimes(priceZone, startTimes);
        var electricityPrices = electricityPriceRepository.findByPriceZoneAndStartTimes(priceZone, startTimes);

        return estimateResultingEmissionAndPrice(consumption, emissions, electricityPrices);
    }

    private List<ElectricityDataPoint> estimateResultingEmissionAndPrice(
                List <ElectricityConsumption> consumption, 
                List<EmissionEntity> emissions, 
                List<ElectricityPriceEntity> electricityPrices
    ) {
        var timeStartToAvgEmission = timeStartToAvgEmission(emissions);
        var timeStartToAvgElectricityPrice = timeStartToAvgElectricityPrice(electricityPrices);

        return consumption.stream().map(entry -> {
            var priceMatch = timeStartToAvgElectricityPrice.get(entry.getTimeStart());
            var emissionMatch = timeStartToAvgEmission.get(entry.getTimeStart());
            return ElectricityDataPointMapper.mapToDataPoint(entry, priceMatch, emissionMatch);
        }).toList();
    }
    
    private Map<ZonedDateTime, EmissionAverageResult> timeStartToAvgEmission(List<EmissionEntity> emissions) {
        return emissions.stream()
                .collect(Collectors.groupingBy(
                        EmissionEntity::getTimeStart,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    int avgGCO2ePerKWh = (int) list.stream()
                                            .mapToInt(EmissionEntity::getGCO2ePerKWh)
                                            .average()
                                            .orElse(0);

                                    return EmissionAverageResult.builder()
                                            .timeStart(list.getFirst().getTimeStart())
                                            .timeEnd(list.getFirst().getTimeEnd())
                                            .avgGCO2ePerKWh(avgGCO2ePerKWh)
                                            .build();
                                }
                        )
                ));
    }

    private Map<ZonedDateTime, ElectricityPriceAverageResult> timeStartToAvgElectricityPrice(
            List<ElectricityPriceEntity> electricityPrices) {
        return electricityPrices.stream()
                .collect(Collectors.groupingBy(
                        ElectricityPriceEntity::getTimeStart,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    double avgSekPerKWh = list.stream()
                                            .mapToDouble(ElectricityPriceEntity::getSekPerKWh)
                                            .average()
                                            .orElse(0);

                                    double avgEurPerKWh = list.stream()
                                            .mapToDouble(ElectricityPriceEntity::getEurPerKWh)
                                            .average()
                                            .orElse(0);

                                    return ElectricityPriceAverageResult.builder()
                                            .timeStart(list.getFirst().getTimeStart())
                                            .timeEnd(list.getFirst().getTimeEnd())
                                            .avgSekPerKWh(avgSekPerKWh)
                                            .avgEurPerKWh(avgEurPerKWh)
                                            .build();
                                }
                        )
                ));
    }
}
