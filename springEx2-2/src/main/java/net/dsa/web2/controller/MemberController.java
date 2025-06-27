package net.dsa.web2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Member;
import net.dsa.web2.service.MemberService;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	MemberService ms;

	@GetMapping("join")
	public String join() {
		return "member/join";
	}
	
	@PostMapping("join")
	public String memberJoin(Member member) {
		
		log.debug("회원가입 데이터: {}", member);
		ms.save(member);
		
		return "redirect:/";
	}
	
	@GetMapping("list")
	public String list(Model model) {
		
		model.addAttribute("memberList",ms.getList());
		
		return "member/list";
	}
	
}
