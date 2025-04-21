package com.eloptimeraren.business_logic.api.request;

import com.eloptimeraren.shared.enums.PriceZone;
import jakarta.validation.Valid;
import lombok.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeRequest {

    @Valid
    @NotEmpty(message = "consumption must not be empty")
    @NotNull(message = "consumption must not be null")
    List<ElectricityConsumption> consumption;

    @NotNull(message = "priceZone must not be null")
    PriceZone priceZone;

}
