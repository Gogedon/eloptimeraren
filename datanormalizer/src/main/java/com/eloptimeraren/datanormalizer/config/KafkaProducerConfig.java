package com.eloptimeraren.datanormalizer.config;

import com.eloptimeraren.datanormalizer.dto.NormalizedElectricityPrice;
import com.eloptimeraren.datanormalizer.dto.NormalizedEmission;
import com.eloptimeraren.shared.config.KafkaProducerBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Configuration
public class KafkaProducerConfig extends KafkaProducerBaseConfig {

    @Bean
    public KafkaTemplate<String, List<NormalizedElectricityPrice>> normalizedElectricityPriceKafkaTemplate() {
        return new KafkaTemplate<>(buildProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, List<NormalizedEmission>> normalizedEmissionKafkaTemplate() {
        return new KafkaTemplate<>(buildProducerFactory());
    }
}