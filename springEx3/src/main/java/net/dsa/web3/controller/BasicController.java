package net.dsa.web3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.MemberDTO;
import net.dsa.web3.service.MemberService;

@Controller
@Slf4j
@RequestMapping("basic")

@RequiredArgsConstructor
public class BasicController {
	
	// 1. 필드 주입
	// @Autowired
	// MemberService ms;

	// 2. 생성자 주입
	// private MemberService ms;
	// @Autowired
	// public BasicController(MemberService, ms) {
	//	this.ma = ms;
	// }
	
	//3. 명시적인 생성자 사용
	// lombok은 final이 붙은 필드를 대상으로 자동으로 생성자 매개변수를 만듦
		private final MemberService ms;

	 /* 저장 테스트
	 * @return 메인화면으로 이동
	 */
	@GetMapping("insertData")
	public String insertData() {
		log.debug("[controller-insert] insertData 접근");
		
		ms.insertData();
		
		
		return "redirect:/";
	}
	
	/**
	 * 조회 테스트 (임의의 id로 회원정보 조회)
	 * @return 메인화면으로 이동
	 */
	
	@GetMapping("selectData")
	public String selectData() {
		
		MemberDTO memberDTO = ms.selectData("bbb");
		log.debug("[controller-select] memberDTO : {}", memberDTO);
		
		
		return "redirect:/";
	}
	
	/**
	 * 수정 테스트 (임의의 회원정보를 생성해서 회원정보 수정)
	 * @return 메인화면으로 이동
	 */
	@GetMapping("updateData")
	public String updateData() {
		
		MemberDTO m = MemberDTO.builder()
					  .id("bbb")	
					  .pw("123")
					  .name("손흥민")
					  .phone("010-6666-7777")
					  .address("코엑스")
					  .build();
		
		ms.updateData(m);		
		log.debug("[controller-update] memberDTO : {}", m);
					  
		
		return "redirect:/";
		
	}
	
	/**
	 * 삭제 테스트(임이의 id를 가진 회원정보 삭제)
	 * @return 메인화면으로 이동
	 */
	@GetMapping("deleteData")
	public String deleteData() {
		
		boolean result = ms.deleteData("bbb");
		
		if (result)
			log.debug("[controller-delete] 삭제되었습니다.");
		else
			log.debug("[controller-delete] 회원이 없습니다.");
		
		return "redirect:/"; 
	}
	
	/**
	 * 모든 회원정보 보기
	 * @return 메인화면으로 이동
	 */
	@GetMapping("selectAllData")
	public String selectAllData() {
		
		List<MemberDTO> memberList = ms.selectAllData();
		
		for(MemberDTO dto : memberList) {
			log.debug("[controller-selectAll} memberList) : {}", dto);
		}
		
		return "redirect:/";
	}
}
