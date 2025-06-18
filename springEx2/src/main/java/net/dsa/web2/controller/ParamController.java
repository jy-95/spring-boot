package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Person;
import net.dsa.web2.dto.PersonForm;

@Slf4j
@Controller
@RequestMapping("param")	//공통적으로 처리되는 서브경로를 갖도록 만들어주는 어노테이션
public class ParamController {

	// param/view1
	@GetMapping("view1")
	public String view1() {
		return "param/view1";
	}
	
	// http://localhost:9992/web2/param/param1
	//			?name=abc&age=99&phone=01011112222
	@GetMapping("param1")
	public String param1(
			@RequestParam(name = "name") String name,	//밑줄이 나오는 이유. 이름이 똑같으면 괄호 안 생략 가능하다.
			@RequestParam(name ="age") int age,
			@RequestParam(name = "phone") String phone
			) {
		
		log.debug("param1 log = name: {}, age: {}, phone:{}", name, age, phone);
		return "redirect:/";	//루트(/)라는 경로를 다시 찾아가겠다는 뜻
	}
	
	@GetMapping("view2")
	public String view2() {
		return "param/view2";
	}
	
	@PostMapping("param2")
	public String param2(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "age") int age,
			@RequestParam(name = "phone") String phone
			) {
		
		log.debug("param2 log = name: {}, age: {}, phone:{}", name, age, phone);
		
		return "redirect:/";
	}
	
	@GetMapping("view3")
	public String view3() {
		return "param/view3";
	}
	
	@PostMapping("param3")
	public String param3(Person p) {		//dto로 만들어둔 구조체로 쉽게 데이터를 이동시킬 수 있다.
		log.debug("param3 log = person: {}",p);
		
		return "redirect:/";
	}
	
	@GetMapping("param4")
	public String param4(Person p) {
		log.debug("param4 log = person:{}",p);
		
		return "redirect:/";
	}
	
	@GetMapping("param5")
	public String param5(
		@RequestParam(name = "name", defaultValue = "아무개") String name,
		@RequestParam(name = "age", defaultValue = "0") int age,
		@RequestParam(name = "phone", defaultValue = "01011112222") String phone) {
		
		log.debug("param5 log = name: {}, age: {}, phone: {}", name, age, phone);
		return "redirect:/";
	}
	
	
	// import org.springframework.ui.Model;
	// Model : 컨트롤러에서 뷰(JSP, Thymeleaf 등)로 데이터를 넘겨주는데 사용되는 데이터 전달 객체
	// Controller에서 HTML로 넘어가는 순간만 유효한 1회성 객체구조
	// *redirect:/ 등의 재요청에서는 저장된 데이터가 없어짐
	
	@GetMapping("model")
	public String model(Model model) {		//model은 스프링에서 기본 제공. 스프링이 집어넣어주는 객체 구조. 임포트 시 springframework로 임포트
		String str = "문자열";
		int num = 100;
		Person p = new Person("홍길동", 33, "01011112222");
		
		model.addAttribute("str", str);
		model.addAttribute("num", num);
		model.addAttribute("person",p);
		
		return "param/model";
	}
	
	@GetMapping("view4")
	public String view4(Model model) {
		model.addAttribute("person", new PersonForm());
		return "param/view4";
	}
	
	// @Validated : PersonForm 클래스에 설정된 어노테이션 유효성 검사를 자동 실행
	// @BindingResult : 유효성 검사 결과를 담는 객체, 에러가 있으면 여기 등록
	// @ModelAttribute : ex. person 으로 Model 객체에 자동 등록
	@PostMapping("validation")
	public String validation(@Validated @ModelAttribute("person") PersonForm personForm, BindingResult result  // @Validated 바로 뒤에 위치해야 함
			){
		// 1. Annotation 기반 검증 후 에러가 하나라도 있으면 view4로 이동
		if (result.hasErrors()) {
			return "param/view4";
		}
		// 2. 추가적인 유효성 검사
		if (personForm.getPhone().contains("-")) {
			result.reject("SyntaxError", "전화번호 형식이 맞지 않습니다");
			return "param/view4";
		}
		log.debug("가입 성공!");
		return "redirect:/";
	}

}
