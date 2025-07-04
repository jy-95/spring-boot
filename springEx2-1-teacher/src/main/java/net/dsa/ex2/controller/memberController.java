package net.dsa.ex2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.extern.slf4j.Slf4j;
import net.dsa.ex2.dto.Member;
import net.dsa.ex2.service.MemberService;

@Controller
@Slf4j
@RequestMapping("member")
public class memberController {

	@Autowired
	MemberService ms;
	
	/**
	 * 회원가입 처리
	 */
	@GetMapping("join")
	public String join() {
		return "member/join";
	}
	
//	@PostMapping("memberJoin")
//	public String memberJoin(Member p, HttpSession session) {
//		session.setAttribute("member", p);
//		return "redirect:/";
//	}
	
	@PostMapping("memberJoin")
	public String memberJoin(Member p) {		
		
		ms.addMember(p);
		return "redirect:/";
	}
	
	@GetMapping("list")
	public String getList(Model model) {
		model.addAttribute("memberList",ms.getMemberList());
		
		return "member/list";
	}
}