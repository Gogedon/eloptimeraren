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
public class NormalizedElectricityPrice {
    private String source;
    private Double sekPerKWh;
    private Double eurPerKWh;
    private Double exr; //exchange rate
    private ZonedDateTime timeStart;
    private ZonedDateTime timeEnd;
    private ZonedDateTime normalizedAt;
    private PriceZone priceZone;
}
