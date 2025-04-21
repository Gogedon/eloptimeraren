package com.eloptimeraren.fetch_electricitymaps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ElectricitymapsEmissionCollection {
    @JsonProperty("zone")
    private String zone;

    @JsonProperty("history")
    private List<ElectricitymapsEmission> electricitymapsEmissionList;
}
