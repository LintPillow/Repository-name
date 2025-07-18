package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.ArrivalEntity;
import com.example.demo.entity.DepartureEntity;
import com.example.demo.repository.ArrivalRepository;
import com.example.demo.repository.DepartureRepository;

@Controller
public class PunchController {

	@Autowired
	private ArrivalRepository arrivalRepository;

	@Autowired
	private DepartureRepository departureRepository;

	@PostMapping("/")
	public String submitPunch(@RequestParam("userName") String userName, @RequestParam("action") String action,
			Model model) {

		LocalDateTime now = LocalDateTime.now();

		if ("arrival".equals(action)) {
			ArrivalEntity arrival = new ArrivalEntity();
			arrival.setUserName(userName);
			arrival.setTimestamp(now);
			arrivalRepository.save(arrival);
		} else if ("departure".equals(action)) {
			DepartureEntity departure = new DepartureEntity();
			departure.setUserName(userName);
			departure.setTimestamp(now);
			departureRepository.save(departure);
		}

		model.addAttribute("userName", userName);
		model.addAttribute("timestamp", now);
		return "punch/punchCompletion";
	}

	//
	// // (任意)
	// @GetMapping("/punch/completion")
	// public String showCompletionPage() {
	// return "punch/punchCompletion";
	// }
}