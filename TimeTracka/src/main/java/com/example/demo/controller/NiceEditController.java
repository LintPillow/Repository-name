package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.AttendanceDAO;
import com.example.demo.entity.ArrivalEntity;
import com.example.demo.entity.DepartureEntity;
import com.example.demo.repository.ArrivalRepository;
import com.example.demo.repository.DepartureRepository;
import com.example.demo.service.NiceAdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NiceEditController {

	private final ArrivalRepository arrivalRepository;
	private final DepartureRepository departureRepository;

	@GetMapping("/niceadminedit")
	public String showEditPage(@RequestParam String userName, @RequestParam String date,
			org.springframework.ui.Model model) {

		LocalDate parsedDate = LocalDate.parse(date);
		LocalDateTime start = parsedDate.atStartOfDay();
		LocalDateTime end = parsedDate.atTime(LocalTime.MAX);

		Optional<ArrivalEntity> arrivalOpt = arrivalRepository.findByUserNameAndDate(userName, start, end);
		Optional<DepartureEntity> departureOpt = departureRepository.findByUserNameAndDate(userName, start, end);

		model.addAttribute("userName", userName);
		model.addAttribute("date", date);
		model.addAttribute("arrivalTime", arrivalOpt.map(e -> e.getTimestamp().toLocalTime().toString()).orElse(""));
		model.addAttribute("departureTime", departureOpt.map(e -> e.getTimestamp().toLocalTime().toString()).orElse(""));

		return "admin/niceadminedit";
	}

	@GetMapping("/niceadminhome")
	public String showNiceAdminHome() {
		return "admin/niceadminhome";
	}
}

