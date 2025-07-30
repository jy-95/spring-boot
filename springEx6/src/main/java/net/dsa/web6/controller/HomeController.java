package net.dsa.web6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	// @ResponseBody				// 이 메서드는 요청으로부터 들어온 값, 혹은 출력 하고자 하는 리턴 값에 대해 데이터를 전송할 때 사용
	@GetMapping({"", "/"})
	public String main() {
		return "home";
	}

}
