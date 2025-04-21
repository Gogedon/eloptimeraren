package com.eloptimeraren.fetch_elpris.config;

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

    @Bean
    public NewTopic elprisTopic() {
        return new NewTopic(KafkaTopics.ELECTRICITY_PRICE_ELPRIS_TOPIC.value, 1, (short) 1);
    }
}