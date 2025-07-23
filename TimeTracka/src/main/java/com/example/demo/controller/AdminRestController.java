//package com.example.demo.controller;
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.dao.AttendanceDAO;
//import com.example.demo.service.NiceAdminService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//public class AdminRestController {
//
//	private final NiceAdminService niceAdminService;
//
//	@GetMapping("/api/attendances")
//	public List<AttendanceDAO> getAttendances() {
//		return niceAdminService.getAllAttendances();
//	}
//}
