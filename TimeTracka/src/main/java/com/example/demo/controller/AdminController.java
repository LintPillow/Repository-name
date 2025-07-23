package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

	@GetMapping("/admin")
	public String showloginForm() {
		return "admin/admin";
	}

	@PostMapping("/admin")
	public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {

		if (username.equals("admin") && password.equals("adminpass")) {
			return "admin/adminhome";
		} else {
			model.addAttribute("error", "usernameかpasswordが間違っています");
			return "/admin/admin";
		}
	}

//	@GetMapping("/adminedit")
//	public String editPunch() {
//		return "admin/adminEdit";
//	}
	
	@GetMapping("/adminedit")
	public String editPunch(@RequestParam("id") Long id, @RequestParam("name")String name, Model model) {
	    model.addAttribute("id", id);
	    model.addAttribute("name", name);
	    
	    return "admin/adminEdit"; 
	}
	
	
}
