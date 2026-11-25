package com.finpo.api.etf;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="etf")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Etf {
    @Id private String code;
    private String isin;
    private String name;
    @Enumerated(EnumType.STRING) private AssetClass assetClass;
    @Enumerated(EnumType.STRING) private Market market;
    @Enumerated(EnumType.STRING) private TrackingMultiple trackingMultiple;
    private String issuer;
    private BigDecimal expenseRatio;
    private LocalDate listedDate;
    private String indexName;
    private String sector;
    private boolean active;
    private LocalDateTime updatedAt;
}
