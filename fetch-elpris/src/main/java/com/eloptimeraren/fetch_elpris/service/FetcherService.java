package com.eloptimeraren.fetch_elpris.service;

import com.eloptimeraren.fetch_elpris.dto.ElprisElectricityPrice;
import com.eloptimeraren.shared.enums.PriceZone;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FetcherService {

    private static final Logger log = LoggerFactory.getLogger(FetcherService.class);
    private static final String ELPRIS_API_TEMPLATE = "https://www.elprisetjustnu.se/api/v1/prices/%s/%s_%s.json";

    private final WebClient webClient;
    private final PublisherService publisherService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleFetch() {
        log.info("Fetching electricity price data from API...");

        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());
        String monthDay = currentDate.format(DateTimeFormatter.ofPattern("MM-dd"));
        Arrays.stream(PriceZone.values()).forEach(priceZone ->
                fetchElectricityPriceData(String.format(ELPRIS_API_TEMPLATE, year, monthDay, priceZone.name()), priceZone));
    }

    private void fetchElectricityPriceData(String apiUrl, PriceZone priceZone) {
        log.info("Fetching electricity price data from URL: {}", apiUrl);

        webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(ElprisElectricityPrice[].class)
                .onErrorResume(e -> {
                    log.error("Error occurred while fetching electricity prices data: {}", e.getMessage(), e);
                    return Mono.empty();
                })
                .subscribe(response -> {
                    if (response != null) {
                        List<ElprisElectricityPrice> elprisElectricityPrices = Arrays.asList(response);
                        elprisElectricityPrices.forEach(electricityPrice -> electricityPrice.setPriceZone(priceZone));
                        log.info("Fetched {} elpris records for priceZone {}.", elprisElectricityPrices.size(), priceZone);
                        publisherService.publish(elprisElectricityPrices);
                    } else {
                        log.warn("API returned null response.");
                    }
                });
    }

}