-- schema.sql
--テーブルを削除してから作成

DROP TABLE IF EXISTS arrivals;
CREATE TABLE arrivals (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  userName VARCHAR(255),
  timestamp TIMESTAMP(0)
);

DROP TABLE IF EXISTS departures;
CREATE TABLE departures (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  userName VARCHAR(255),
  timestamp TIMESTAMP(0)
);