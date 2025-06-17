package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Person;

@Slf4j
@Controller
public class LombokController {
	
	@GetMapping("lombok/lombok1")
	public String lombok1() {
		
		log.debug("lombok1 실행");
		
		// 기존 방식
		Person p = new Person();
		p.setName("홍길동");
		p.setAge(20);
		p.setPhone("01011112222");		//person에 setter를 만들지 않았음에도 롬복이 자동으로 해주고 있다. toString도 자동 적용된다.
		log.debug("Person : {}", p);	//log는 데이터도 출력할 수 있다. 변수가 가지고 있는 값을 출력하고 싶다면 중괄호를 사용한다.
		
		// Lombok builder 패턴
		Person ps = Person.builder()
					.name("이순신")
					.age(99)
					.phone("01033334444")
					.build();
		log.debug("Person2: {}", ps);
		
		return "lombok/lombok1";
	}
	
	@GetMapping("lombok/lombok2")
	public String lombok2() {
		
		log.error("error");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");	//lombok2가 있는 패키지 web2는 프로퍼티스에서 debug 단계로 설정했으므로 trace 로그는 출력되지 않는다.
		
		String str = "로그로그";
		log.debug("debug: {}", str);		//str의 내용이 중괄호 안에 출력된다.
		
		return "lombok/lombok2";
	}

}
