package com.eloptimeraren.shared.enums;

public enum KafkaTopics {
    // Fetcher <-> Normalizer
    ELECTRICITY_PRICE_ELPRIS_TOPIC("electricity.price.elpris.feed", Category.ELECTRICITY_PRICE),
    ELECTRICITY_PRICE_MGREY_TOPIC("electricity.price.mgrey.feed", Category.ELECTRICITY_PRICE),
    EMISSIONS_ELECTRICITY_MAPS_TOPIC("emissions.electricity.maps.feed", Category.EMISSIONS),
    // Normalizer <-> BusinessLogic
    ELECTRICITY_PRICE_NORMALIZED_TOPIC("electricity.price.normalized.feed", Category.NORMALIZED),
    EMISSIONS_NORMALIZED_TOPIC("emissions.normalized.feed", Category.NORMALIZED);

    public final String value;
    public final Category category;

    KafkaTopics(String value, Category category) {
        this.value = value;
        this.category = category;
    }
}
