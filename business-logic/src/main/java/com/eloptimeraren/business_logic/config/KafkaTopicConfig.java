package com.eloptimeraren.business_logic.config;

import com.eloptimeraren.shared.config.KafkaTopicBaseConfig;
import com.eloptimeraren.shared.enums.KafkaTopics;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig extends KafkaTopicBaseConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return buildKafkaAdmin();
    }

    @Bean
    public NewTopic normalizedElectricityPriceTopic() {
        return new NewTopic(KafkaTopics.ELECTRICITY_PRICE_NORMALIZED_TOPIC.value, 1, (short) 1);
    }

    @Bean
    public NewTopic normalizedEmissionsTopic() {
        return new NewTopic(KafkaTopics.EMISSIONS_NORMALIZED_TOPIC.value, 1, (short) 1);
    }

}