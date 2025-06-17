package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Person;

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
}
