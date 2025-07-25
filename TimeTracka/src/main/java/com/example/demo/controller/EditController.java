package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.EditForm;
import com.example.demo.service.EditService;

import lombok.RequiredArgsConstructor;



//編集画面のコントローラ
@Controller 
@RequiredArgsConstructor
public class EditController {
	
	private final EditService editService;
	
	@GetMapping("/adminedit")
    public String edit(@RequestParam String name, @RequestParam String date , org.springframework.ui.Model model) {
		model.addAttribute("name" , name);
		model.addAttribute("date" , date);
	 	return "/admin/adminEdit";
	 }
	
	@PostMapping("/adminedit/content")
	public String update(@ModelAttribute EditForm form) {
		
//		htmlからpostされたデータを各データ型に成形
	    String name = form.getName();
	    LocalDate date = LocalDate.parse(form.getDate()); // "2025-07-25"
	    LocalTime arrivalTime = LocalTime.parse(form.getArrivalTime()); // "09:00"
	    LocalTime departureTime = LocalTime.parse(form.getDepartureTime()); // "17:00"
	    
	    LocalDateTime arrivalDateTime = LocalDateTime.of(date, arrivalTime);
	    LocalDateTime departureDateTime = LocalDateTime.of(date, departureTime);

	    
	    editService.findArrival(name, date).ifPresent(arrival -> {
	        arrival.setTimestamp(arrivalDateTime);
	        editService.saveArrival(arrival);
	    });
	    
	    editService.findDeparture(name, date).ifPresent(departure ->{
	    	departure.setTimestamp(departureDateTime);
	    	editService.saveDeparture(departure);
	    });
	    return "redirect:/admin/adminhome";
	}
	
	
	
	 
}