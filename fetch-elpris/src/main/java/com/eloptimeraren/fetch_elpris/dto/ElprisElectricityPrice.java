package com.eloptimeraren.fetch_elpris.dto;
import com.eloptimeraren.shared.enums.PriceZone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ElprisElectricityPrice {

    @JsonProperty("SEK_per_kWh")
    private double sekPerKWh;

    @JsonProperty("EUR_per_kWh")
    private double eurPerKWh;

    @JsonProperty("EXR")
    private double exr;

    @JsonProperty("time_start")
    private ZonedDateTime timeStart;

    @JsonProperty("time_end")
    private ZonedDateTime timeEnd;

    @JsonProperty("price_zone")
    private PriceZone priceZone;
}