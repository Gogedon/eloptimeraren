package com.eloptimeraren.business_logic.api.response;

import com.eloptimeraren.shared.enums.PriceZone;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AnalysisResponse {

    PriceZone priceZone;
    String aggregationPeriod;
    DataPointResponse mostExpensiveDataPoint;
    DataPointResponse highestEmissionDataPoint;
    List<DataPointResponse> dataPoints;

}
