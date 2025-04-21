package com.eloptimeraren.datanormalizer.dto;

import com.eloptimeraren.shared.enums.PriceZone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalizedEmission {
    private String source;
    private int gCO2ePerKWh;
    private ZonedDateTime timeStart;
    private ZonedDateTime timeEnd;
    private ZonedDateTime normalizedAt;
    private PriceZone priceZone;
}
