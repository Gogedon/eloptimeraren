package com.eloptimeraren.datanormalizer.config;

import com.eloptimeraren.shared.config.KafkaConsumerBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig extends KafkaConsumerBaseConfig {

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return buildConsumerFactory();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
        return buildListenerContainerFactory();
    }
}