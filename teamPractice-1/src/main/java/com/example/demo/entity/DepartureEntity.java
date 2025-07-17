package com.example.demo.entity;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name="departures")
@Data
//退勤打刻Entity
public class DepartureEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String user_name;
	@Column(name = "date_time", columnDefinition = "TIMESTAMP")
	private Timestamp date_Time;
}
