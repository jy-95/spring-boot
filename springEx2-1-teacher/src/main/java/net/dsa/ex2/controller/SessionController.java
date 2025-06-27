package net.dsa.ex2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex2.dto.Member;
import net.dsa.ex2.service.MemberService;

@Controller
@Slf4j
@RequestMapping("session")
public class SessionController {
	
	@Autowired
	MemberService ms;

	
	@GetMapping("login")
	public String login(Model model,
			
		@CookieValue (name = "id", defaultValue = "") String id) {
		
		model.addAttribute("id", id);
		
		return "session/login";
	}
	
	@PostMapping("login")			
	public String loginProcess(
			HttpServletResponse response,
			HttpSession session,
			@RequestParam("id") String id,
			@RequestParam("pw") String pw,
			@RequestParam(name = "rememberId",defaultValue = "false") boolean rememberId
			) {
		
		if(rememberId) {
			Cookie cookie = new Cookie("id",id);
			response.addCookie(cookie);
		}else {
			Cookie cookie = new Cookie("id",id);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		List<Member> memberList = ms.getMemberList();
		for(Member member : memberList) {
			if(member.getId().equals(id) && member.getPw().equals(pw)) {
				session.setAttribute("loginId", id);
				return "redirect:/"; 
			}
		}
		
		log.debug("로그인되지 않았습니다");
		return "session/login";
		
		
		
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		session.invalidate();    // 세션 완전 종료
		return "redirect:/";		
	}
}
