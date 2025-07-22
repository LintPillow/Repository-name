-- schema.sql
CREATE TABLE arrivals (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  userName VARCHAR(255),
  timestamp TIMESTAMP(0)
);

CREATE TABLE departures (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  userName VARCHAR(255),
  timestamp TIMESTAMP(0)
);