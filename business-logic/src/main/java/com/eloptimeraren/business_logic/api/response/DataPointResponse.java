package com.eloptimeraren.business_logic.api.response;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class DataPointResponse {

    ZonedDateTime timeStart;
    ZonedDateTime timeEnd;
    Double costSek;
    Double costEur;
    Double carbonEmissionGrams;
    double kiloWatt;
    DataPointBackgroundResponse background;

}
