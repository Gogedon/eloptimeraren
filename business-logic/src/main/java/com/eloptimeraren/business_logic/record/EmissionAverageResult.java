package com.eloptimeraren.business_logic.record;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record EmissionAverageResult(ZonedDateTime timeStart, ZonedDateTime timeEnd, int avgGCO2ePerKWh) { }