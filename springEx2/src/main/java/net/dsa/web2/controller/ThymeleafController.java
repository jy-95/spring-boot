package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Person;

@Controller
@Slf4j
@RequestMapping("thymeleaf")
public class ThymeleafController {

	@GetMapping("thymeleaf1")
	public String thymeleaf(Model model) {
		
		String str = "문자열";
		int num = 300;
		Person p = new Person("김철수", 33, "010-1111-2222");
		String tag = "<marquee>html 태그</marquee>";
		String url = "http://google/com";
		
		model.addAttribute("str", str);
		model.addAttribute("num", num);
		model.addAttribute("person", p);
		model.addAttribute("tag", tag);
		model.addAttribute("url", url);
		
		
		return "thymeleaf/thymeleaf1";
	}
}
