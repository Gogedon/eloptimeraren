package com.eloptimeraren.fetch_electricitymaps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ElectricitymapsEmission {
    @JsonProperty("zone")
    private String zone;

    @JsonProperty("carbonIntensity")
    private int carbonIntensity; //gCO2e/kWh

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("emissionFactorType")
    private String emissionFactorType;

    @JsonProperty("isEstimated")
    private boolean isEstimated;

    @JsonProperty("estimationMethod")
    private String estimationMethod;
}
