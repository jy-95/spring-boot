package net.dsa.web3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.service.MemberService;

@Controller
@Slf4j
@RequestMapping("basic")
@RequiredArgsConstructor
public class BasicController {

	private final MemberService ms;	
	
	@GetMapping("insertData")
	public String insertData() {
		
		ms.insertData();
		
		return "redirect:/";
	}
	
	@GetMapping("selectData")
	public String selectData() {
		
		ms.selectData();
	}
	
}
