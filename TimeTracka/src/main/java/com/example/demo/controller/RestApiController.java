package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

		List<Map<String, Object>> dummyList = new ArrayList<Map<String, Object>>();
		dummyList.add(dummyMap);

		System.out.println(dummyList);
		return dummyList;
	}

//	出勤のデータをデータベースから取ってくるAPI
	@GetMapping("/api/arrivals")
	public List<ArrivalEntity> showArrivalDashboard() {
		return adminService.getAllArrivals();
	}

//	退勤データをデータベースから取ってくるAPI
	@GetMapping("/api/departures")
	public List<DepartureEntity> showDepartureDashboard() {
		return adminService.getAllDepartures();
	}

//	出発時刻のdummyデータをgetするAPIcontroller
	@GetMapping("/api/departures/dummy")
	public List<Map<String, Object>> showPunchDummyDeparturesForm() {

//		dummyデータの作成
		Map<String, Object> dummyMap = new HashMap<String, Object>();
		dummyMap.put("id", "1");
		dummyMap.put("名前", "山田花子");
		dummyMap.put("退勤時間", "2025-07-18 17:55");
		List<Map<String, Object>> dummyList = new ArrayList<Map<String, Object>>();
		dummyList.add(dummyMap);
		System.out.println(dummyList);
		return dummyList;
	}

//	出勤データの削除
	@DeleteMapping("api/arrivals/delete/{id}")
	public List<ArrivalEntity> deleteArrivalPunch(@PathVariable Long id ) {
		
		System.out.println("Arrival の一件削除: ID = " + id);
		return adminService.deleteArrival(id);
	}
	
//	退勤データの削除
	@DeleteMapping("api/departures/delete/{id}")
	public List<DepartureEntity> deleteDeparturePunch(@PathVariable Long id) {
		
		System.out.println("Departure の一件削除: ID = " + id);
		return adminService.deleteDepartures(id);
	}

}
