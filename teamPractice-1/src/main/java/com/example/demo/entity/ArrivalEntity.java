package com.example.demo.entity;

import java.security.Timestamp;

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
//	打刻id
	private int id;
//	ユーザー名
	private String user_name;
//	出勤時間
	private Timestamp dateTime;
}
