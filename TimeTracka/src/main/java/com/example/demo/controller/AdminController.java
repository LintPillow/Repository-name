package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
		
	@GetMapping("/admin")
	public String showloginForm() {
		return "admin/admin";
	}
	
	
//	@GetMapping("/")
//	public String authenticate() {
//		return "adminhome";
//	}
	
	@RequestMapping("/adminhome")
	public String showDashboard(Model model) {
		
		
		
		
		return "admin/adminhome";
    }
	
	@RequestMapping("/adminedit")
	public String editPunch() {
		return "admin/adminedit";
	}
	
//	@DeleteMapping("/")
//	public String deletePunch() {
//		return "adminhome";
//	}

}
