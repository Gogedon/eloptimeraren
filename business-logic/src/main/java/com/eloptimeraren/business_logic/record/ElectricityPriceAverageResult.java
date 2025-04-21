package com.eloptimeraren.business_logic.record;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ElectricityPriceAverageResult(ZonedDateTime timeStart,
                                            ZonedDateTime timeEnd,
                                            double avgSekPerKWh,
                                            double avgEurPerKWh) { }