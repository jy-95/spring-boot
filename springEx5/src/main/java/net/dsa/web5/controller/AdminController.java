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
	
	/**
	 * 회원목록 조회
	 * @param model
	 * @return list.html
	 */
	@GetMapping("list")
	public String list(Model model) {
		
		try {
			List<MemberDTO> dto = ms.selectAll();
			model.addAttribute("memberList",dto);
			log.debug("회원목록: {}", dto);
		} catch (Exception e) {
			log.debug("[예외 발생] 회원목록 조회 실패..");
		}
		
		return "admin/list";
	}
	
	/**
	 * 권한 변경
	 * @param id 권한을 변경하고자 하는 아이디
	 * @return 회원목록
	 */
	@GetMapping("update")
	public String update(@RequestParam (name="id") String id) {
		try {
			ms.changeRole(id);			
			log.debug("권한 변경 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생] 권한 변경 실패..");
		}
		
		return "redirect:/admin/list";
	}
	
	/**
	 * 계정상태 변경 처리
	 * @param id
	 * @param enabled
	 * @return 회원목록
	 */
	@GetMapping("enabled")
	public String editEnabled(@RequestParam (name="id") String id, @RequestParam (name="enabled") boolean enabled) {
		
		try {
			ms.changeEnabled(id, !enabled);
			log.debug("{} 계정의 상태를 {}로 변경", id, enabled);
		} catch (Exception e) {
			log.debug("[예외 발생] 계정 상태 변경 실패...");
		}
		
		return "redirect:/admin/list";
	}
}
