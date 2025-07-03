package net.dsa.web3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
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
	
	/**
	 * 로그인 페이지로 이동
	 * @return 로그인 페이지로 이동
	 */
	@GetMapping("login")
	public String loginForm() {
		return "member/loginForm";
	}
	
	/**
	 * 로그인 처리
	 * @param id		로그인 페이지에서 입력한 아이디
	 * @param pw		로그인 페이지에서 입력한 패스워드
	 * @param HttpSession	로그인 유저의 아이디를 저장할 세션 객체
	 * @return 메인페이지 or 로그인페이지
	 */
	@PostMapping("login")
	public String login(
			@RequestParam(name = "id") String id,
			@RequestParam(name = "pw") String pw,
			HttpSession session
			) {
		
		/*
		 * 받아온 파라미터 id, pw를 DB의 회원정보 중 일치하면
		 * 세션 객체에 id를 저장하고 메인페이지로 이동
		 * DB에 일치하는 회원정보가 없으면 로그인 페이지로 이동
		 */
		
		try {
			boolean result = ms.loginCheck(id,pw);
			
			if(result) {
				session.setAttribute("loginId", id);
				String resultId = (String) session.getAttribute("loginId");
				log.debug("로그인 성공! 현재 세션정보: {}",resultId);
				return "redirect:/"; 
			} else {
				log.debug("로그인 실패! 비밀번호 불일치");
				return "member/loginForm";
			}
		} catch (Exception e) {
			log.debug("[예외 발생] 로그인 실패! DB에 저장된 정보가 없음");
			return "member/loginForm";
		}
		
//		List<MemberDTO> memberList = ms.selectAllData();
//		for(MemberDTO member : memberList) {
//			if(member.getId().equals(id) && member.getPw().equals(pw)) {
//				session.setAttribute("loginId", id);
//				return "redirect:/"; 
//			}
//		}
//		
//		
//		return "member/loginForm";
	}
	
	/**
	 * 로그아웃 처리
	 * @param HttpSession
	 * @return 메인페이지로 이동
	 */
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		String sessionId = (String) session.getAttribute("loginId");
		log.debug("로그아웃, 현재 세션정보: {}", sessionId);
		return "redirect:/";
	}
	
	/**
	 * 회원정보 수정페이지로 이동
	 * @param	HttpSession
	 * @param	Model
	 * @return 	회원정보 수정페이지
	 */
	@GetMapping("update")
	public String updateForm(HttpSession session, Model model) {

		String id = (String) session.getAttribute("loginId");
		if (id != null) {
		MemberDTO member = ms.selectData(id);
		log.debug("현재 접속 중인 유저의 회원정보: {}", member);
		model.addAttribute("member", member);
		}
		return "member/updateForm";
	}
	/**
	 * 회원정보 수정 처리
	 * @param MemberDTO 수정할 회원정보
	 * @return 메인페이지로 이동
	 */
	@PostMapping("update")
	public String update(MemberDTO member) {
		
		log.debug("수정할 회원정보: {}", member);
		try {
			ms.updateData(member);
			log.debug("수정 성공!");
		} catch (Exception e) {
			log.debug("수정 실패! 회원이 존재하지 않습니다.");
		}
		
		return "redirect:/";
	}
	
	/**
	 * 회원정보 조회 폼으로 이동
	 * @return 입력페이지로 이동
	 */
	@GetMapping("select")
	public String select() {
		return "member/selectForm";
	}
	/**
	 * 검색폼에서 입력한 아이디를 전달받아 회원정보 조회
	 * @param 	id 		조회할 아이디
	 * @param 	model	
	 * @return	select.html
	 */
	@PostMapping("select")
	public String select(
			@RequestParam (name="id") String id,
			Model model) {
		
		log.debug("조회할 ID: {}", id);
		if(id != null) {
		MemberDTO member = ms.selectData(id);
		log.debug("회원정보: {}", member);
		
		model.addAttribute("member", member);
		model.addAttribute("searchId",id);
		}
		return "member/select";
	}
	
	/**
	 * URL로부터 들어온 요청을 처리하는 메서드
	 * http://localhost:9993/web3/member/info/abc
	 * @param 	id	조회할 아이디
	 * @param 	Model
	 * @return	select.html
	 */
	@GetMapping({"info" + "/{id}", "info"})
	public String info(
			@PathVariable(name = "id", required = false) String id,
			Model model
			) {
		/*
		 	@PathVariable
		 	URL 경로 자체에 포함된 값을 파라미터로 받아오는 방식
		 	 @RequestParam		/member?id=abc&pw=123	쿼리스트링
		 	 @PathVariable		/member/abc				(물음표가 없는) 경로 변수
		 */
		log.debug("경로 변수: {}", id);
		if (id != null) {
			MemberDTO member = ms.selectData(id);
			log.debug("회원정보: {}", member);
			
			
			model.addAttribute("searchId", id);
			model.addAttribute("member", member);
		}
		return "member/select";
	}
	
	@GetMapping("list")
	public String list(Model model) {
		
		List<MemberDTO> list = ms.selectAllData();
		log.debug("회원정보 전체조회: {}", list);
		model.addAttribute("list", list);
		
		return "member/list";
	}
	/**
	 * 회원정보 삭제 처리
	 * @param  	id	삭제할 id
	 * @return	list.html
	 */
	@GetMapping("delete")
	public String delete(@RequestParam(name = "id") String id) {
		
		log.debug("삭제할 id: {}", id);
		
		ms.deleteData(id);
		
		// 삭제 시 리스트는 갱신된다. redirect 없이 member/list를 하면 위에 있는 메서드를 통해 데이터를 가져오지도 못하고 출력도 못한다.
		// 전체 회원 목록을 가져와서 모델에 저장하는 과정을 한 번 더 하기 위해 redirect가 필요하다.
		return "redirect:/member/list";
	}
}
