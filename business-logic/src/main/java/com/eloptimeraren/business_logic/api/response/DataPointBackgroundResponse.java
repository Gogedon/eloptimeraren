package com.eloptimeraren.business_logic.api.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DataPointBackgroundResponse {

    Double sekPerKWh;
    Double eurPerKWh;
    Integer carbonEmissionGramsPerKWh;

}
