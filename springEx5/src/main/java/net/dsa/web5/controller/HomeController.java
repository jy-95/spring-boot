package net.dsa.web5.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@GetMapping({"", "/"})
	public String home() {
		return "home";
	}

	
	/*
	 * @AuthenticationPrincipal
	 	- Spring Security가 직접 로그인한 사용자 정보를 주입
	 	- 로그인 성공 > SecurityContext에 Authentication 객체 저장
	 */
	@GetMapping("thymeleaf")
	public String thymeleaf(
			@AuthenticationPrincipal UserDetails user) {
		
		log.debug("Authentication 객체 정보 출력");
		log.debug(user.getUsername());		// id값
		log.debug(user.getPassword());
		log.debug("" + user.getAuthorities());
		log.debug("" + user.isAccountNonExpired());
		log.debug("" + user.isAccountNonLocked());
		log.debug("" + user.isCredentialsNonExpired());
		log.debug("" + user.isEnabled());
		
		return "thymeleaf";
	}
	
	//WebSecurityConfig 에서 예외핸들러를 적었다면 해당 경로를 처리하는 메서드도 필요
	@GetMapping("/error/403")
	public String error403() {
		log.debug("error 403 - 권한이 없습니다.");
		return "error/403";
	}
}