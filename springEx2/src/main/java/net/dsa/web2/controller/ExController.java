package net.dsa.web2.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Student;

@Controller
@Slf4j
@RequestMapping("ex")
public class ExController {

	
	@GetMapping("info")
	public String info() {
		return "ex/info";
	}
	
	@GetMapping("infoIn")
	public String infoIn(Model model,
			@RequestParam(name = "name") String name,	
			@RequestParam(name ="RN") String RN) {
		
		
		int birthYear = Integer.parseInt(RN.substring(0, 2));
		int birthMonth = Integer.parseInt(RN.substring(2, 4));
		int birthDay = Integer.parseInt(RN.substring(4, 6));
		String Year = RN.substring(0, 2);
		
		char genderCode = RN.charAt(7);
		String gender = "";
		
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int age = 0;
		
		switch (genderCode) {
		case '1': case '2':
			gender = (genderCode == '1') ? "남자" : "여자";
			age = y - birthYear - 1900;
			break;
		case '3': case '4':
			gender = (genderCode == '3') ? "남자" : "여자";
			age = y - birthYear - 2000;
			break;
		}
		
		
		model.addAttribute("name", name);
		model.addAttribute("BY", Year);
		model.addAttribute("BM", birthMonth);
		model.addAttribute("BD", birthDay);
		model.addAttribute("Gender", gender);
		model.addAttribute("age", age);
		
		
		return "ex/infoOutput";
	}


//	@GetMapping("infoOutput")
//	public String infoOutput() {
//		return "ex/infoOutput";
//	}
//	
	@GetMapping("count")
	public String count(HttpServletResponse response,
						Model model,
						@CookieValue(name = "count", defaultValue = "0") int count) {
		
		count++;
		model.addAttribute("count",count);
		
		//var ttt = Integer.toString(count);
		Cookie c1 = new Cookie("count", Integer.toString(count));	//덮어쓰기
		c1.setMaxAge(60*60*24*3);
		response.addCookie(c1);
		
		return "ex/count";
	}
	
	@GetMapping("darkmode")
	public String darkmode() {
		return "ex/darkmode";
	}
	
	@GetMapping("temp")
	public String temp() {
		return "ex/temp";
	}
	
	@GetMapping("thymeleaf")
	public String thymeleafPrint(Model model) {
		List<Student> list = List.of(
			new Student("홍길동", 20, "010-1111-2222", 30),
			new Student("김철수", 21, "010-3333-4444", 90),
			new Student("이영희", 22, "010-5555-6666", 65)
		);
		model.addAttribute("studentList",list);
				
		return "ex/print";
	}
		
}
