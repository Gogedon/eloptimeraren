package com.eloptimeraren.fetch_mgrey.service;

import com.eloptimeraren.fetch_mgrey.dto.MgreyElectricityPriceCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FetcherService {

    private static final Logger log = LoggerFactory.getLogger(FetcherService.class);
    private static final String MGREY_API_TEMPLATE = "https://mgrey.se/espot?format=json&date=%s-%s";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final PublisherService publisherService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleElectricityPriceData() {
        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());
        String monthDay = currentDate.format(DateTimeFormatter.ofPattern("MM-dd"));
        fetchElectricityPriceData(String.format(MGREY_API_TEMPLATE, year, monthDay));
    }

    private void fetchElectricityPriceData(String apiUrl) {
        log.info("Fetching mgrey electricity price data from URL: {}", apiUrl);

        webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class) // Retrieve as String due to Response-type header being text/html; charset=UTF-8
                .onErrorResume(e -> {
                    log.error("Error occurred while fetching electricity prices data: {}", e.getMessage(), e);
                    return Mono.empty();
                })
                .subscribe(response -> {
                    if (response != null) {
                        try {
                            // Map the raw String response to MgreyElectricityPriceCollection using ObjectMapper
                            MgreyElectricityPriceCollection mgreyElectricityPrices = objectMapper.readValue(response, MgreyElectricityPriceCollection.class);
                            mgreyElectricityPrices.getPricesPerZone().forEach((priceZone, prices) ->
                                    log.info("Fetched {} mgrey records for priceZone {}.", prices.size(), priceZone.name())
                            );
                            publisherService.publish(mgreyElectricityPrices);
                        } catch (IOException e) {
                            log.error("Error parsing JSON response: {}", e.getMessage());
                        }
                    } else {
                        log.warn("API returned null response.");
                    }
                });
    }

}