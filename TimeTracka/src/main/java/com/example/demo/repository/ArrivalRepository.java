package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.ArrivalEntity;

public interface ArrivalRepository extends JpaRepository<ArrivalEntity, Long> {
	@Query("SELECT a FROM ArrivalEntity a WHERE a.userName = :userName AND a.timestamp BETWEEN :start AND :end")
	Optional<ArrivalEntity> findByUserNameAndDate(
			@Param("userName") String userName,
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end);

	@Modifying
	@Query("DELETE FROM ArrivalEntity a WHERE a.userName = :userName AND a.timestamp BETWEEN :start AND :end")
	void deleteByUserNameAndDate(@Param("userName") String userName,
	                             @Param("start") LocalDateTime start,
	                             @Param("end") LocalDateTime end);
	
	
}