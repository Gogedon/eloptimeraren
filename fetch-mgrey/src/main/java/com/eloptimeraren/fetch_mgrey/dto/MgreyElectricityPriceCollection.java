package com.eloptimeraren.fetch_mgrey.dto;

import com.eloptimeraren.shared.enums.PriceZone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MgreyElectricityPriceCollection {

    @JsonProperty("date")
    private String date;

    @JsonProperty("SE1")
    private List<MgreyElectricityPrice> se1;

    @JsonProperty("SE2")
    private List<MgreyElectricityPrice> se2;

    @JsonProperty("SE3")
    private List<MgreyElectricityPrice> se3;

    @JsonProperty("SE4")
    private List<MgreyElectricityPrice> se4;

    @JsonIgnore
    public Map<PriceZone, List<MgreyElectricityPrice>> getPricesPerZone() {
        return Map.of(PriceZone.SE1, se1, PriceZone.SE2, se2, PriceZone.SE3, se3, PriceZone.SE4, se4);
    }

    @JsonIgnore
    public int getTotalSize() {
        return se1.size() + se2.size() + se3.size() + se4.size();
    }
}