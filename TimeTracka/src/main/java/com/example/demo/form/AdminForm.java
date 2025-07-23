package com.example.demo.form;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdminForm {
	
	private Long arrivalId;
	private Long departureId;
	
	public String name;
	
	public LocalDateTime arrivalTime;
	public LocalDateTime departureTime;
}
