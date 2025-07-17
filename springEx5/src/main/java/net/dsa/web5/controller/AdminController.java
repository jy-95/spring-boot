package net.dsa.web5.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.MemberDTO;
import net.dsa.web5.service.MemberService;

@Controller
@Slf4j
@RequestMapping("admin")
@RequiredArgsConstructor
/*
  	해당 클래스의 모든 메서드 실행 '전'에 Spring Security가 hasRole('ADMIN')조건 검사.
 */
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	private final MemberService ms;
	
	@GetMapping("page")
	public String admin() {
		return "admin/adminPage";
	}
	
	@GetMapping("list")
	public String list(@AuthenticationPrincipal UserDetails user, Model model) {
		
		String userId = user.getUsername();
		List<MemberDTO> dto = ms.selectAll(userId);
		model.addAttribute("memberList",dto);
		
		return "admin/list";
	}
	
	@GetMapping("editRole")
	public String editRole(@RequestParam (name="id") String id) {
		
		ms.changeRole(id);
		
		return "redirect:/admin/list";
	}
	
	@GetMapping("editEnabled")
	public String editEnabled(@RequestParam (name="id") String id) {
		
		ms.changeEnabled(id);
		
		return "redirect:/admin/list";
	}
}
