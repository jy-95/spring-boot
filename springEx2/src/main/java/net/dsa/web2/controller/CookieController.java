package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("cookie")
public class CookieController {
	
/*
 	[ cookie ]
 	클라이언트(브라우저)에 저장되는 데이터
 	설정된 만료 시간까지 유지
 	
 	- 저장 위치: 클라이언트의 웹 브라우저에 저장
 	- 크기 제한: 일반적으로 하나의 쿠키는 4KB 이하의 데이터 저장
 	- 유효 기간: 쿠키에는 만료 날짜가 포함. 만료 날짜가 지나면 쿠키 삭제
 	- 보안: 쿠키는 텍스트 형식으로 클라이언트 측에 저장되므로 보안에 취약
 */
	
	// 쿠키 저장
	@GetMapping("cookie1")
	public String cookie1(HttpServletResponse response) {
		// 쿠키 객체로부터 객체 생성
		Cookie c1 = new Cookie("str","abcde");		//str이라는 이름(키)으로 abcde라는 데이터(밸류)를 가지고 있다.
		Cookie c2 = new Cookie("num", "12345");
		
		// 쿠키 수명, setMaxAge(초)는 쿠키의 유효기간을 초 단위로 설정
		c1.setMaxAge(60*60*24*3);
		c2.setMaxAge(60*60*24*3);
		
		// 생성한 쿠키를 HTTP 응답에 추가하여 클라이언트(브라우저)에 전송
		response.addCookie(c1);
		response.addCookie(c2);
		
		return "redirect:/";
	}
	
	// 쿠키 삭제
	@GetMapping("cookie2")
	public String cookie2(HttpServletResponse response) {
		Cookie c1 = new Cookie("str", null);		// 덮어쓰기로 삭제시킨다.
		Cookie c2 = new Cookie("num", null);
		
		c1.setMaxAge(0);	//생존 시간을 0초로 한다.
		c2.setMaxAge(0);
		
		response.addCookie(c1);
		response.addCookie(c2);
		
		return "redirect:/";
	}
	
	// @CookieValue : 요청시 HTTP 요청 헤더에서 Cookie 값을 추출하여 매핑
	// 쿠키 읽기
	@GetMapping("cookie3")
	public String cookie3(
			@CookieValue(name = "str", defaultValue = "없음") String str,
			@CookieValue(name = "num", defaultValue = "0") int num
			) {
			
			log.debug("cookie값 확인 = str: {}, num: {}", str, num);
			
			return "redirect:/";
	}
}
