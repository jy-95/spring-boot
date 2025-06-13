package net.dsa.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// http://localhost:9991/web1
	@GetMapping({"", "/"})
	public String home() {
		return "home"; 
	}
}
