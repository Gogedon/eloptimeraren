package com.eloptimeraren.shared.config;

import org.springframework.beans.factory.annotation.Value;

public class KafkaBaseConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    protected String bootstrapAddress;

}
