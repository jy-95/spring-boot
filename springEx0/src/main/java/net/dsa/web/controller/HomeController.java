package net.dsa.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// http://localhost:포트번호/루트경로/~~
	// 루트 경로 이후 오는 문자열을 읽고 아무것도 없거나 슬래시가 있다면 이 메서드에 매핑시키겠다.
	@GetMapping({"", "/"})
	public String home() {
		return "home"; // home + .html
	}
}

