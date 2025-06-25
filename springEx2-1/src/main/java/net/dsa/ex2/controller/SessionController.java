package net.dsa.ex2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("session")
public class SessionController {

	
	@GetMapping("login")
	public String login() {
		return "session/login";
	}
	
	@PostMapping("login")			//위에서도 login이라는 같은 경로이지만 get, post 방식이 다르므로 겹쳐서 적을 수 있다.
	public String loginProcess(
			HttpSession session,
			@RequestParam("id") String id,
			@RequestParam("password") String pw
			) {
		
		if(id.equals("abc") && pw.equals("123")) {
			session.setAttribute("loginId", id);
			return "redirect:/";
		} else {
			log.debug("로그인되지 않았습니다");
			return "session/login";
		}
		
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		session.invalidate();    // 세션 완전 종료
		return "redirect:/";		
	}
}
