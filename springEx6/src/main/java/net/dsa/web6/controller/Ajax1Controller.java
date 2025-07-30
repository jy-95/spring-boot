package net.dsa.web6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class Ajax1Controller {
	
	/**
	 * ajaxTest.html 이동
	 */
	@GetMapping("ajax1")
	public String ajax1() {
		return "ajax/ajaxTest1";
	}
	
	/**
	 * Ajax 요청에 대한 응답 1
	 */
	//@ResponseBody : 데이터 응답 전용 Annotation
	//				  컨트롤러의 메서드 반환값을 HTTP 응답 본문에 기록하여 전송
	//				  Thymeleaf 같은 뷰 렌더링 없이, 데이터 자체를 전송
	@ResponseBody
	@GetMapping("ajaxTest1")
	public void ajaxTest1() {
		log.debug("ajaxTest1 실행");
		return;
	}
	
	/**
	 * Ajax 요청에 대한 응답 2
	 * @param str 문자열
	 */
	@ResponseBody
	@PostMapping("ajaxTest2")
	public void ajaxTest2(@RequestParam("str") String str) {
		log.debug("ajaxTest2 출력 : {}", str);
	}
	
	/**
	 * AJax 요청에 대한 응답 3
	 * @return 문자열
	 */
	@ResponseBody
	@GetMapping("ajaxTest3")
	public String ajaxTest3() {
		String msg = "서버에서 보낸 메세지";
		log.debug("ajaxTest3에서 보냄: {}", msg);
		return msg;
	}
}
