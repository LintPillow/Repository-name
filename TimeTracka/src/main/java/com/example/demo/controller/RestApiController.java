package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestApiController {

//	到着時刻のdummyデータをgetするAPIcontroller
	@GetMapping("/api/arrivals/dummy")
	public List<Map<String, Object>> showPunchDummyArrivalForm() {
		
//		dummyデータの作成
		Map<String, Object> dummyMap = new HashMap<String, Object>();
		dummyMap.put("id", "1");
		dummyMap.put("名前", "山田花子");
		dummyMap.put("出勤時間", "2025-07-18 08:55");
		
		List<Map<String, Object>> dummyList = new ArrayList<Map<String,Object>>();
		dummyList.add(dummyMap);
		
		System.out.println(dummyList);
		return dummyList;
	}
	
	出発時刻のdummyデータをgetするAPIcontroller
	@GetMapping("/api/departures/dummy")
	public List<Map<String, Object>> showPunchDummyDeparturesForm() {
		
//		dummyデータの作成
		Map<String, Object> dummyMap = new HashMap<String, Object>();
		dummyMap.put("id", "1");
		dummyMap.put("名前", "山田花子");
		dummyMap.put("退勤時間", "2025-07-18 17:55");
		List<Map<String, Object>> dummyList = new ArrayList<Map<String,Object>>();
		dummyList.add(dummyMap);
		System.out.println(dummyList);
		return dummyList;
	}
	
//	dummyデータの削除
	
}
