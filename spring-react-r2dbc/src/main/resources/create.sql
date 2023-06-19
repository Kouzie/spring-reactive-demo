CREATE TABLE stock_info
(
    id           LONG,
    code         VARCHAR(256) UNIQUE NOT NULL,
    name         VARCHAR(256),
    price        INTEGER,
    views        INTEGER,
    volume       LONG,
    market_price INTEGER,
    change_rate  DOUBLE,
    updated      LONG
);