package net.dsa.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MmmmController {

	@GetMapping({"","/"})
	public String home() {
		return "home";
	}
}
