package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "arrivals")
@Data

//出勤打刻Entity
public class ArrivalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 打刻id
	private Long id;
	// ユーザー名
	@Column(name = "USERNAME")
	private String userName;

	// 出勤時間
	@Column(name = "timestamp", columnDefinition = "TIMESTAMP")
	private LocalDateTime timestamp;
}