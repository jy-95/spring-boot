package net.dsa.web2.controller;

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
import net.dsa.web2.service.MemberService;

@Controller
@Slf4j
@RequestMapping("session")
public class SessionController {
	
	@Autowired
	MemberService ms;

	@GetMapping("login")
	public String login(Model model,
			@CookieValue(name = "recentId", defaultValue = "") String recentId) {
		model.addAttribute("recentId", recentId);
		log.debug("쿠키 저장 = {}",recentId);
		
		return "session/login";
	}
	
	@PostMapping("login")
	public String loginform(HttpSession session,
			@RequestParam(name = "id") String id,
			@RequestParam(name = "pw") String pw,
			@RequestParam(name = "logincheck", defaultValue = "false") boolean check,
			HttpServletResponse response) {
		
		boolean result = ms.loginCheck(id, pw);
		if(check) {
		Cookie c1 = new Cookie("recentId", id);
		c1.setMaxAge(60*60*24);
		response.addCookie(c1);
		} else {
			Cookie c1 = new Cookie("recentId", "");
			c1.setMaxAge(0);
			response.addCookie(c1);
		}
		
		if(result) {
			session.setAttribute("loginId",id);
		} else {
			return "redirect:/";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		session.invalidate();
		return "redirect:/";
	}
}
