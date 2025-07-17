package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ArrivalEntity;

public interface ArrivalRepository extends JpaRepository<ArrivalEntity, Long> {
}