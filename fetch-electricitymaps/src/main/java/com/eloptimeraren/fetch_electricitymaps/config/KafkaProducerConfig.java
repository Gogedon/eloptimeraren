package com.eloptimeraren.fetch_electricitymaps.config;

import com.eloptimeraren.fetch_electricitymaps.dto.ElectricitymapsEmissionCollection;
import com.eloptimeraren.shared.config.KafkaProducerBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig extends KafkaProducerBaseConfig {

    @Bean
    public KafkaTemplate<String, ElectricitymapsEmissionCollection> electricitymapsEmissionsKafkaTemplate() {
        return new KafkaTemplate<>(buildProducerFactory());
    }
}