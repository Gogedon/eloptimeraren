package com.eloptimeraren.datanormalizer.config;

import com.eloptimeraren.shared.config.KafkaTopicBaseConfig;
import com.eloptimeraren.shared.enums.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig extends KafkaTopicBaseConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return buildKafkaAdmin();
    }

    /* From fetcher services */
    @Bean
    public NewTopic elprisElectricityPriceTopic() {
        return new NewTopic(KafkaTopics.ELECTRICITY_PRICE_ELPRIS_TOPIC.value, 1, (short) 1);
    }

    @Bean
    public NewTopic mgreyElectricityPriceTopic() {
        return new NewTopic(KafkaTopics.ELECTRICITY_PRICE_MGREY_TOPIC.value, 1, (short) 1);
    }

    @Bean
    public NewTopic electricitymapsEmissionsTopic() {
        return new NewTopic(KafkaTopics.EMISSIONS_ELECTRICITY_MAPS_TOPIC.value, 1, (short) 1);
    }

    /* To business logic */
    @Bean
    public NewTopic normalizedElectricityPriceTopic() {
        return new NewTopic(KafkaTopics.ELECTRICITY_PRICE_NORMALIZED_TOPIC.value, 1, (short) 1);
    }

    @Bean
    public NewTopic normalizedEmissionsTopic() {
        return new NewTopic(KafkaTopics.EMISSIONS_NORMALIZED_TOPIC.value, 1, (short) 1);
    }

}