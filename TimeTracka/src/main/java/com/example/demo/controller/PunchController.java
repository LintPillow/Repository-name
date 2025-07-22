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

	@PostMapping("/")
	public String submitPunch(@RequestParam("userName") String userName, @RequestParam("action") String action,
			Model model) {

		// バリデーション：名前が空 / null の場合はエラー表示
		if (userName == null || userName.trim().isEmpty()) {
			model.addAttribute("error", "名前を入力してください");
			return "punch/punchIndex";
		}

		LocalDateTime now = LocalDateTime.now().withNano(0); // ミリ秒以下カット

		switch (action) {
		case "arrival" -> {
			ArrivalEntity arrival = new ArrivalEntity();
			arrival.setUserName(userName);
			arrival.setTimestamp(now);
			arrivalRepository.save(arrival);
		}
		case "departure" -> {
			DepartureEntity departure = new DepartureEntity();
			departure.setUserName(userName);
			departure.setTimestamp(now);
			departureRepository.save(departure);
		}
		default -> {
			model.addAttribute("error", "不正な操作です");
			return "punch/punchIndex";
		}
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