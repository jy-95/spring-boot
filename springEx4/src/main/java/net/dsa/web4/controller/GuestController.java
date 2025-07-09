package net.dsa.web4.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web4.dto.GuestBookDTO;
import net.dsa.web4.service.GuestService;

@Controller
@Slf4j
@RequestMapping("guest")
@RequiredArgsConstructor
public class GuestController {

	private final GuestService service;
	
	@GetMapping("write")
	public String writeform() {
		return "guest/writeForm";
	}
	/**
	 * 작성한 글 전달받아 저장
	 * @param 	입력폼에서 작성한 내용
	 * @return	메인페이지
	 */
	@PostMapping("write")
	public String write(GuestBookDTO dto) {
		log.debug("작성한 정보: {}", dto);
		
		try {
			service.save(dto);
			log.debug("글 작성 성공!");
		} catch (Exception e) {
			log.debug("글 작성 실패...");
		}
		// service에서는 트랜젝션을 이용해서 정상적으로 작동 시 커밋, 아니면 롤백이 되기에 예외가 발생하지 않는다.
		// 이에 컨트롤러에서 예외를 만들고 관리한다.
		
		return "redirect:/";
	}
	/**
	 * 글 목록 보기
	 * @param model
	 * @return guestList.html
	 */
	@GetMapping("guestList")
	public String guestbook(Model model) {
		
		List<GuestBookDTO> guestList = service.selectAll();
		
		// 로그 출력
		for (GuestBookDTO guest : guestList) {
			log.debug("글 정보: {}", guest);			
		}
		
		// home.html에서 출력하기위해 Model 객체에 저장
		model.addAttribute("guestList", guestList);
		
		// 리턴 문자열에 .html을 붙여 src/main/resources 의 templates 패키지 로부터 찾음
		return "guest/guestList";

	}
	
	/**
	 * 글 삭제
	 * @param password 입력한 비밀번호
	 * @param num 삭제할 글 번호
	 * @param RedirectAttributes
	 * 			리다이렉트 할 때 오류 메시지를 저장 및 전달할 객체
	 * @return 글 목록 페이지로 이동
	 */
	@PostMapping("delete")
	public String delete(GuestBookDTO guestbook, RedirectAttributes ra) {
		try {
			service.delete(guestbook);
			
		} catch (Exception e) {
			ra.addFlashAttribute("msg", "삭제 실패했습니다.");
		}
		
		return "redirect:/guest/guestList";
	}
}
