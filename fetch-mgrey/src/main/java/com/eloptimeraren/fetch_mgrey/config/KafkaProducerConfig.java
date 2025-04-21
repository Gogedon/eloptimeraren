package com.eloptimeraren.fetch_mgrey.config;

import com.eloptimeraren.fetch_mgrey.dto.MgreyElectricityPriceCollection;
import com.eloptimeraren.shared.config.KafkaProducerBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig extends KafkaProducerBaseConfig {

    @Bean
    public KafkaTemplate<String, MgreyElectricityPriceCollection> mgreyElectricityPriceCollectionKafkaTemplate() {
        return new KafkaTemplate<>(buildProducerFactory());
    }
}