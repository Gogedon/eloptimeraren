package com.eloptimeraren.business_logic.domain;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class ElectricityDataPoint {

    ZonedDateTime timeStart;
    ZonedDateTime timeEnd;
    Double costSek;
    Double costEur;
    Double carbonEmissionGrams;
    double kiloWatt;
    ElectricityDataPointBackground background;

}
