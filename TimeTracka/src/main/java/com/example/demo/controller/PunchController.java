package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	// 打刻ページの表示
	@GetMapping("/")
	public String showPunchForm(Model model) {
		model.addAttribute("timestamp", LocalDateTime.now());
		return "punch/punchIndex";
	}

	// 出勤処理
	@PostMapping("/punch/arrival")
	public String submitArrival(@RequestParam("userName") String userName, Model model) {
		ArrivalEntity arrival = new ArrivalEntity();
		arrival.setUserName(userName);
		arrival.setTimestamp(LocalDateTime.now());
		arrivalRepository.save(arrival);

		model.addAttribute("userName", userName);
		model.addAttribute("timestamp", arrival.getTimestamp());
		return "punch/punchCompletion";
	}

	// 退勤処理
	@PostMapping("/punch/departure")
	public String submitDeparture(@RequestParam("userName") String userName, Model model) {
		DepartureEntity departure = new DepartureEntity();
		departure.setUserName(userName);
		departure.setTimestamp(LocalDateTime.now());
		departureRepository.save(departure);

		model.addAttribute("userName", userName);
		model.addAttribute("timestamp", departure.getTimestamp());
		return "punch/punchCompletion";
	}

	//
	// // (任意)
	// @GetMapping("/punch/completion")
	// public String showCompletionPage() {
	// return "punch/punchCompletion";
	// }
}