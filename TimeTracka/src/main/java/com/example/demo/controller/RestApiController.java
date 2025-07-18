package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ArrivalEntity;
import com.example.demo.entity.DepartureEntity;
import com.example.demo.service.AdminService;


@RestController
public class RestApiController {

	  @Autowired
	    private AdminService adminService;

//	出勤のdummyデータをgetするAPIcontroller
	@GetMapping("/api/arrivals/dummy")
	public List<Map<String, Object>> showPunchDummyArrivalForm() {

//		dummyデータの作成
		Map<String, Object> dummyMap = new HashMap<String, Object>();
		dummyMap.put("id", "1");
		dummyMap.put("名前", "山田花子");
		dummyMap.put("出勤時間", "2025-07-18 08:55");
	}

//	出勤のデータをデータベースから取ってくるAPI
	@GetMapping("/api/arrivals")
	public List<ArrivalEntity> showArrivalDashboard(){
		return adminService.getAllArrivals();
	}

//	退勤データをデータベースから取ってくるAPI
	@GetMapping("/api/departures")
	public List<DepartureEntity> showDepartureDashboard(){
		return adminService.getAllDepartures();
	}


}
