package net.dsa.web6.controller;

import org.springframework.http.ResponseEntity;
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
	/**
	 * Ajax 요청에 대한 응답 4
	 * @param num1 정수1
	 * @param num2 정수2
	 * @return 전달받은 정수를 연산한 결과
	 */
	@ResponseBody
	@PostMapping("ajaxTest4")
	public int ajaxTest4(
			@RequestParam("num1") int a, 
			@RequestParam("num2") int b) {
		log.debug("ajax에게서 전달받은 값: {}, {}", a, b);
		
		return a + b;
	}
	/**
	 * ajax 요청에 대한 응답 5
	 * @param 정수1
	 * @param 정수2
	 * @return 연산 결과 or ResponseEntity<?>
	 */
	/*
	 * 		ResponseEntity<?>
	 * 			Spring에서 HTTP 응답 전체를 구성할 수 있게 해주는 클래스
	 * 			- 응답 본문(body), 상태 코드(status), 헤더(headers) 포함 가능
	 * 			- REST API 응답 처리에 사용되는 방법 중 하나
	 * 			- 내부적으로 @ResponseBody를 포함함
	 */
	@ResponseBody
	@PostMapping("ajaxTest5")
	public ResponseEntity<?> ajaxTest5(
			@RequestParam("num4") String a,
			@RequestParam("num5") String b){
		log.debug("ajaxTest5 전달받은 값: {}, {}", a, b);
		
		try {
			int n1 = Integer.parseInt(a);
			int n2 = Integer.parseInt(b);
			int n3 = n1 / n2;
			
			//HTTP 응답상태코드 200 + 계산결과를 본문으로 리턴
			return ResponseEntity.ok(n3);
		} catch (NumberFormatException e) { //정수로 받아올 수 없는 값이 들어왔을 때
			log.debug("[예외 발생] {}", e.getMessage());
			
			//HTTP 응답상태코드 400 + 에러 메시지를 본문으로 리턴
			return ResponseEntity.badRequest().body("정수가 아닙니다.");
		} catch(ArithmeticException e){
			log.debug("[예외 발생 ] {}", e.getMessage());
			
			return ResponseEntity.badRequest().body("0으로 나눌 수 없습니다.");
		}
	}
	/**
	 * 요청에 대한 응답 6
	 * @param num7
	 * @param num8
	 * @param op
	 * @return ResponseEntity<?>
	 */
//	@ResponseBody		(작성하지 않아도 ResponseEntity<?> 안에 내부적으로 있다)
	@PostMapping("ajaxTest6")
	public ResponseEntity<?> ajaxTest6(
			@RequestParam("num7") String a,
			@RequestParam("num8") String b,
			@RequestParam("op") String op){
		log.debug("ajaxTest6 전달받은 값: {}, {}, {}", a, b, op);
		
		try {
			int n1 = Integer.parseInt(a);
			int n2 = Integer.parseInt(b);
			int n3 = 0;
			
			switch (op) {
				case "+" : n3 = n1 + n2;	break;
				case "-" : n3 = n1 - n2;	break;
				case "*" : n3 = n1 * n2;	break;
				case "/" : n3 = n1 / n2;	break;
				default:
					return ResponseEntity.badRequest().body("연산자를 확인해주세요."); // 연산자 선택을 안한 경우
			}
			
			return ResponseEntity.ok(n3);
			
			
		} catch (NumberFormatException e) {
			log.debug("[예외 발생] {}", e.getMessage());
			
			return ResponseEntity.badRequest().body("정수가 아닙니다.");
		} catch (ArithmeticException e) {
			log.debug("[예외 발생] {}", e.getMessage());
			
			return ResponseEntity.badRequest().body("0으로 나눌 수 없습니다.");
		}
	}
}
