package net.dsa.web3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.MemberDTO;
import net.dsa.web3.service.MemberService;

@Controller
@Slf4j
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService ms;
	
	/**
	 * 회원가입 폼으로 이동
	 * @return 입력 HTML 경로
	 */
	
	@GetMapping("join")
	public String join() {
		return "member/join";
	}
	
	/**
	 * 회원가입 처리
	 * @param member 회원가입 정보
	 * @return 메인페이지 이동
	 */
	@PostMapping("join")
	public String join(MemberDTO member) {
		log.debug("Form data 확인: {}", member);
		
		ms.save(member);
		
		return "redirect:/";
	}
}
