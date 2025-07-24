package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


//編集画面のコントローラ
@Controller 
public class EditController {
	
	@GetMapping("/adminedit")
    public String edit(@RequestParam String name, @RequestParam String date , org.springframework.ui.Model model) {
		model.addAttribute("name" , name);
		model.addAttribute("date" , date);
	 	return "/admin/adminEdit";
	 }
	 
}