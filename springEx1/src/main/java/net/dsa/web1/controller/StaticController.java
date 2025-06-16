package net.dsa.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {

	@GetMapping("image")
	public String image() {
		
		System.out.println("image 요청경로에 일치하는 메서드 실행");
		
		return "image";
	}
	
	@GetMapping("sub/image2")	//sub라는 가상경로를 만들었음. 현재 sub라는 경로는 없음. 구분하기 쉽게 하기 위한 목적
	public String image2() {
		// templates 패키지에서 image2.html을 찾으러 가겠다.
		return "image2";
	}
	
	@GetMapping("sub/image3")
	public String image3() {
		//template.sub라는 패키지에서 image3.html을 찾으러 가겠다.
		return "sub/image3";
	}
	
	@GetMapping("css")
	public String css() {
		return "css";
	}
	
	@GetMapping("js")
	public String js() {
		return "js";
	}
}
