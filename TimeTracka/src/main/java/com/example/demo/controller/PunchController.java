package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.Optional;

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

	@GetMapping("/")
	public String showPunchForm(Model model) {
		model.addAttribute("timestamp", LocalDateTime.now());
		return "punch/punchIndex";
	}

	@PostMapping("/")
	public String submitPunch(@RequestParam("userName") String userName, @RequestParam("action") String action,
			@RequestParam(value = "forceType", required = false) String forceType, Model model) {

		if (userName == null || userName.trim().isEmpty()) {
			model.addAttribute("error", "名前を入力してください");
			model.addAttribute("userName", userName);
			return "punch/punchIndex";
		}

		LocalDateTime now = LocalDateTime.now().withNano(0);
		LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
		LocalDateTime endOfDay = startOfDay.plusDays(1);

//		boolean wasOverwritten = false;

		switch (action) {
		case "arrival" -> {
			Optional<ArrivalEntity> existing = arrivalRepository.findByUserNameAndDate(userName, startOfDay, endOfDay);

			if (existing.isPresent() && !"arrival".equals(forceType)) {
				model.addAttribute("error", "本日はすでに出勤打刻されています。上書きする場合は再度出勤ボタンを押してください");
				model.addAttribute("userName", userName);
				model.addAttribute("forceType", "arrival");
				return "punch/punchIndex";
			}

			boolean wasOverwritten = existing.isPresent();
			ArrivalEntity arrival = existing.orElse(new ArrivalEntity());
			arrival.setUserName(userName);
			arrival.setTimestamp(now);
			arrivalRepository.save(arrival);

			if (wasOverwritten) {
				model.addAttribute("wasOverwritten", "打刻情報を上書きしました");
			}
		}

		case "departure" -> {
			Optional<DepartureEntity> existing = departureRepository.findByUserNameAndDate(userName, startOfDay,
					endOfDay);

			if (existing.isPresent() && !"departure".equals(forceType)) {
				model.addAttribute("error", "本日はすでに退勤打刻されています。上書きする場合は再度退勤ボタンを押してください");
				model.addAttribute("userName", userName);
				model.addAttribute("forceType", "departure");
				return "punch/punchIndex";
			}

			boolean wasOverwritten = existing.isPresent();
			DepartureEntity departure = existing.orElse(new DepartureEntity());
			departure.setUserName(userName);
			departure.setTimestamp(now);
			departureRepository.save(departure);

			if (wasOverwritten) {
				model.addAttribute("wasOverwritten", "打刻情報を上書きしました");
			}
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
}
