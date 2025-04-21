package com.eloptimeraren.business_logic.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElectricityDataPointBackground {

    Double sekPerKWh;
    Double eurPerKWh;
    Integer carbonEmissionGramsPerKWh;

}
