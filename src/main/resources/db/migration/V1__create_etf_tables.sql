CREATE TABLE etf (
    code VARCHAR(6) PRIMARY KEY ,
    isin CHAR(12) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    asset_class VARCHAR(20) NOT NULL,
    market VARCHAR(20) NOT NULL,
    tracking_multiple VARCHAR(20) NOT NULL,
    replication VARCHAR(20) NOT NULL,
    issuer VARCHAR(50) NOT NULL,
    expense_ratio DECIMAL(6,4) NOT NULL,
    listed_date DATE NOT NULL,
    index_name VARCHAR(200) NULL,
    sector VARCHAR(50) NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    updated_at DATETIME NOT NULL
);

CREATE TABLE etf_daily (
    etf_code VARCHAR(6) NOT NULL,
    trade_date DATE NOT NULL,
    close_price BIGINT NOT NULL,
    nav DECIMAL(15,2) NULL,
    trading_value BIGINT NOT NULL,
    net_asset BIGINT NULL,
    PRIMARY KEY (etf_code, trade_date),
    CONSTRAINT fk_etf_daily_etf FOREIGN KEY (etf_code) REFERENCES  etf (code)
);