package com.eloptimeraren.fetch_electricitymaps.service;

import com.eloptimeraren.fetch_electricitymaps.dto.ElectricitymapsEmissionCollection;
import com.eloptimeraren.shared.enums.PriceZone;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FetcherService {

    @Value("${api.auth-token}")
    private String authToken;

    private static final Logger log = LoggerFactory.getLogger(FetcherService.class);
    private static final String EM_API_TEMPLATE = "https://api.electricitymap.org/v3/carbon-intensity/history?zone=SE-%s";

    private final WebClient webClient;
    private final PublisherService publisherService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleFetch() {
        log.info("Fetching electricity emission data from API...");
        Arrays.stream(PriceZone.values()).forEach(priceZone ->
                fetchElectricitymapsEmissionData(String.format(EM_API_TEMPLATE, priceZone.name()), priceZone));
    }

    private void fetchElectricitymapsEmissionData(String apiUrl, PriceZone priceZone) {
        log.info("Fetching electricitymaps emission data from URL: {}", apiUrl);

        webClient.get()
                .uri(apiUrl)
                .header("auth-token", authToken)
                .retrieve()
                .bodyToMono(ElectricitymapsEmissionCollection.class)
                .onErrorResume(e -> {
                    log.error("Error occurred while fetching electricitymaps emission data: {}", e.getMessage(), e);
                    return Mono.empty();
                })
                .subscribe(response -> {
                    if (response != null) {
                        log.info("Fetched {} electricitymaps records for priceZone {}.",
                                response.getElectricitymapsEmissionList().size(), priceZone);
                        publisherService.publish(response);
                    } else {
                        log.warn("API returned null response.");
                    }
                });
    }

}