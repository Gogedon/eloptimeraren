package com.eloptimeraren.business_logic.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

// set JsonFormat pattern to ensure timezone is included

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectricityConsumption {

    @NotNull(message = "startTime must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    ZonedDateTime timeStart;

    @NotNull(message = "endTime must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    ZonedDateTime timeEnd;

    @NotNull(message = "kWh must not be null")
    double kiloWatt;
}