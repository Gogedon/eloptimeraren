package com.eloptimeraren.datanormalizer.mapper;

import com.eloptimeraren.datanormalizer.dto.NormalizedElectricityPrice;
import com.eloptimeraren.fetch_elpris.dto.ElprisElectricityPrice;
import com.eloptimeraren.fetch_mgrey.dto.MgreyElectricityPrice;
import com.eloptimeraren.fetch_mgrey.dto.MgreyElectricityPriceCollection;
import com.eloptimeraren.shared.enums.KafkaTopics;
import com.eloptimeraren.shared.enums.PriceZone;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class NormalizedEnergyPriceMapper {

    public static List<NormalizedElectricityPrice> mapToNormalizedLists(List<ElprisElectricityPrice> elprisElectricityPrices) {
        return elprisElectricityPrices.stream().map(elprisEP -> mapToNormalized(elprisEP)).toList();
    }

    public static NormalizedElectricityPrice mapToNormalized(ElprisElectricityPrice elprisEP) {
        return NormalizedElectricityPrice.builder()
                .timeStart(elprisEP.getTimeStart())
                .timeEnd(elprisEP.getTimeEnd())
                .priceZone(elprisEP.getPriceZone())
                .eurPerKWh(elprisEP.getEurPerKWh())
                .sekPerKWh(elprisEP.getSekPerKWh())
                .exr(elprisEP.getExr())
                .normalizedAt(ZonedDateTime.now())
                .source(KafkaTopics.ELECTRICITY_PRICE_ELPRIS_TOPIC.value)
                .build();
    }

    // Map to one list per PriceZone
    public static List<List<NormalizedElectricityPrice>> mapToNormalizedLists(
            MgreyElectricityPriceCollection mgreyElectricityPriceCollection) {
        // mgreyCollection date is in YYYY-MM-DD format
        ZonedDateTime baseDateTime = ZonedDateTime.parse(mgreyElectricityPriceCollection.getDate() + "T00:00:00Z");
        return mgreyElectricityPriceCollection.getPricesPerZone().entrySet().stream()
                .map(entry -> mapToNormalizedList(entry, baseDateTime)).toList();
    }

    private static List<NormalizedElectricityPrice> mapToNormalizedList(
            Map.Entry<PriceZone, List<MgreyElectricityPrice>> entry, ZonedDateTime baseDateTime) {
        return entry.getValue().stream().map(mgreyEP ->
                mapToNormalized(mgreyEP, entry.getKey(), baseDateTime)).toList();
    }

    private static NormalizedElectricityPrice mapToNormalized(
            MgreyElectricityPrice mgreyEP, PriceZone priceZone, ZonedDateTime baseDateTime) {
        ZonedDateTime adjustedDateTimeStart = baseDateTime.plusHours(mgreyEP.getHour());
        ZonedDateTime adjustedDateTimeEnd = baseDateTime.plusHours(mgreyEP.getHour() + 1);
        double adjustedPriceEur = mgreyEP.getPriceEur() / 100; // Convert from cent to EUR
        double adjustedPriceSek = mgreyEP.getPriceSek() / 100; // Convert from öre to SEK

        return NormalizedElectricityPrice.builder()
                .priceZone(priceZone)
                .timeStart(adjustedDateTimeStart)
                .timeEnd(adjustedDateTimeEnd)
                .eurPerKWh(adjustedPriceEur)
                .sekPerKWh(adjustedPriceSek)
                .normalizedAt(ZonedDateTime.now())
                .source(KafkaTopics.ELECTRICITY_PRICE_MGREY_TOPIC.value)
                .build();
    }
}
