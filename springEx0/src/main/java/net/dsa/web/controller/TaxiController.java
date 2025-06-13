package net.dsa.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.dsa.web.service.TransportationService;

@Controller
public class TaxiController {
	
	@Autowired
	@Qualifier("DeluxeTaxiServiceImp1")
	TransportationService ts;
	
	@GetMapping("move") // 경로가 1개면 중괄호 없이 작성, 여러개면 중괄호 사용 후 쉼표로 구분
	public String moveRequest() {
		
		ts.move();
		
		return "redirect:/"; // redirect:/ 는 페이지를 루트 경로로(/ 이후) 다시 요청한다. 리턴값이 html일수도, 아닐수도 있다.
	}
}
