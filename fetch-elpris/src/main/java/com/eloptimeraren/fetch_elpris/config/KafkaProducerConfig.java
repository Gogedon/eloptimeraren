package com.eloptimeraren.fetch_elpris.config;

import com.eloptimeraren.fetch_elpris.dto.ElprisElectricityPrice;
import com.eloptimeraren.shared.config.KafkaProducerBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Configuration
public class KafkaProducerConfig extends KafkaProducerBaseConfig {

    @Bean
    public KafkaTemplate<String, List<ElprisElectricityPrice>> elprisElectricityPriceListKafkaTemplate() {
        return new KafkaTemplate<>(buildProducerFactory());
    }
}