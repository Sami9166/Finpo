package com.finpo.api.etf;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EtfBasicInfo(
        String code,
        String isin,
        String name,
        AssetClass assetClass,
        Market market,
        TrackingMultiple trackingMultiple,
        Replication replication,
        String issuer,
        BigDecimal expenseRatio,
        LocalDate listedDate,
        String indexName
) {}
