package com.niit.ecomm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.niit.ecomm.hiber.domain.MProduct;

@Controller
public class HomeController {

	
	
	@RequestMapping("/")
	public String welcome(Model model) {
		model.addAttribute("greeting", "Welcome to eStore!");
		model.addAttribute("tagline", "Amazingly  new eStore");
		
		return "welcome";
	}
	
	@RequestMapping("/welcome/greeting")
	public String greeting() {
		return "welcome";
	}
}
