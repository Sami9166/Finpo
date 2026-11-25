package com.finpo.api.etf.ingest;
import com.finpo.api.etf.AssetClass;
import com.finpo.api.etf.EtfBasicInfo;
import com.finpo.api.etf.Market;
import com.finpo.api.etf.Replication;
import com.finpo.api.etf.TrackingMultiple;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class EtfMasterImporter {
    private static final DateTimeFormatter LISTED_DATE = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public void importFrom(Path file) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();
        try (Reader reader = Files.newBufferedReader(file, Charset.forName("MS949"));
             CSVParser parser = format.parse(reader)) {
            List<EtfBasicInfo> rows = new ArrayList<EtfBasicInfo>();
            for (CSVRecord r : parser) {
                rows.add(toInfo(r));
            }
            log.info("total {}lines", rows.size());
        }
    }

    private static AssetClass toAssetClass(String v) {
        return switch (v) {
            case "주식" -> AssetClass.EQUITY;
            case "채권" -> AssetClass.BOND;
            case "혼합자산" -> AssetClass.MIXED;
            case "원자재" -> AssetClass.COMMODITY;
            case "부동산" -> AssetClass.REAL_ESTATE;
            case "통화" -> AssetClass.CURRENCY;
            case "기타" -> AssetClass.OTHER;
            default -> throw new IllegalArgumentException("모르는 기초자산분류: " + v);
        };
    }

    private static Market toMarket(String v) {
        return switch (v) {
            case "국내" -> Market.DOMESTIC;
            case "해외" -> Market.OVERSEAS;
            case "국내&해외" -> Market.MIXED;
            default -> throw new IllegalArgumentException("모르는 시장: " + v);
        };
    }

    private static TrackingMultiple toTrackingMultiple(String v) {
        return switch (v) {
            case "일반" -> TrackingMultiple.NORMAL;
            case "2X 레버리지" -> TrackingMultiple.LEVERAGE_2X;
            case "1X 인버스" -> TrackingMultiple.INVERSE_1X;
            case "2X 인버스" -> TrackingMultiple.INVERSE_2X;
            default -> throw new IllegalArgumentException("모르는 추적배수: " + v);
        };
    }

    private static Replication toReplication(String v) {
        return switch (v) {
            case "실물(패시브)" -> Replication.PHYSICAL_PASSIVE;
            case "실물(액티브)" -> Replication.PHYSICAL_ACTIVE;
            case "합성(패시브)" -> Replication.SYNTHETIC_PASSIVE;
            case "합성(액티브)" -> Replication.SYNTHETIC_ACTIVE;
            default -> throw new IllegalArgumentException("모르는 복제방법: " + v);
        };
    }

    private EtfBasicInfo toInfo(CSVRecord r) {
        return new EtfBasicInfo(
                r.get("단축코드"),
                r.get("표준코드"),
                r.get("한글종목약명"),
                toAssetClass(r.get("기초자산분류")),
                toMarket(r.get("기초시장분류")),
                toTrackingMultiple(r.get("추적배수")),
                toReplication(r.get("복제방법")),
                r.get("운용사"),
                new BigDecimal(r.get("총보수")),
                LocalDate.parse(r.get("상장일"), LISTED_DATE),
                r.get("기초지수명")
        );
    }
}
