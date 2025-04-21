package com.eloptimeraren.fetch_mgrey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MgreyElectricityPrice {

    @JsonProperty("hour")
    private int hour;

    @JsonProperty("price_eur")
    private double priceEur;

    @JsonProperty("price_sek")
    private double priceSek;

    @JsonProperty("kmeans")
    private int kmeans;
}