package net.dsa.web5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.MemberDTO;
import net.dsa.web5.service.MemberService;

@Controller
@Slf4j
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService ms;
	
	/**
	 * 요청시 회원가입 페이지로 이동.
	 * @return joinForm.html
	 */
	
	@GetMapping("join")
	public String joinForm() {
		return "member/joinForm";
	}
	
	/**
	 * 회원가입 정보를 받아 회원가입 처리
	 * @param memberDTO
	 * @return home.html
	 */
	@PostMapping("join")
	public String join(MemberDTO member) {
		
		log.debug("전달된 회원정보: {}", member);
		
		try {
			ms.join(member);
			log.debug("가입성공!");
		} catch (Exception e) {
			log.debug("가입실패..");
		}
		
		return "redirect:/";
	}
	
	
	/**
	 * 회원가입 페이지에서 "중복확인" 버튼을 클릭하면 새창으로 보여줄 검색 페이지로 이동
	 * @return idCheck.html
	 */
	@GetMapping("idCheck")
	public String idCheck() {
		return "member/idCheck";
	}
	
	/**
	 * ID 중복확인 페이지에서 검색 요청했을때 처리
	 * @param searchId 검색할 아이디
	 * @param Model
	 * @return idCheck.html
	 */
	@PostMapping("idCheck")
	public String idCheck(
			@RequestParam("searchId") String searchId,
			Model model
			) {
		log.debug("searchId: {}", searchId);
		
		boolean result = ms.idCheck(searchId);
		
		model.addAttribute("result", result);
		model.addAttribute("searchId", searchId);
		
		
		return "member/idCheck";
	}
	
	/**
	 * 로그인 페이지로 이동
	 * 시큐리티에서 인증하기 위해서 보내는 경로값과 일치해야 함.
	 * @return loginForm.html
	 */
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
}
