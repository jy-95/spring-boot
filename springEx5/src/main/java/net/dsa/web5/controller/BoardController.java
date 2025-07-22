package net.dsa.web5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.BoardDTO;
import net.dsa.web5.service.BoardService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
@Controller
public class BoardController {

	private final BoardService bs;
	
	@Value("${board.uploadPath}")
	String uploadPath;
	
	/**
	 * 글쓰기 폼으로 이동
	 */
	@GetMapping("write")
	public String write() {
		return "board/writeForm";
	}
	
	/**
	 * 글 저장
	 * @param boardDTO 작성한 글정보
	 * @param upload 업로드 파일
	 * @param user 로그인한 사용자 정보
	 * @return 게시판 글 목록(메인)
	 */
	@PostMapping("write")
	public String write(
			BoardDTO boardDTO,
			@RequestParam(name = "upload", required = false) MultipartFile upload,
			@AuthenticationPrincipal UserDetails user
			) {
		
		boardDTO.setMemberId(user.getUsername());
		log.debug("저장할 글 정보: {}", boardDTO);
		log.debug("업로드한 파일의 원본 이름: {}", upload.getOriginalFilename());
		log.debug("파일 크기: {}", upload.getSize());
		log.debug("파일이 비어 있는지 여부: {}", upload.isEmpty());
		
		try {
			bs.write(boardDTO, uploadPath, upload);
			log.debug("글 저장 성공!");
			return "redirect:list";
		} catch (Exception e) {
			log.debug("[예외 발생] 글 저장 실패...");
			return "board/writeForm";
		}
	}
	
	/**
	 * 게시판 글 목록 페이지 이동
	 * @param model
	 * @return list.html
	 */
	@GetMapping("list")
	public String list(Model model) {
		
		try {
			List<BoardDTO> dto = bs.selectAll();
			model.addAttribute("boardlist", dto);
			log.debug("글 목록 불러오기 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생] 글 목록 불러오기 실패..");
		}
		
		return "board/list";
	}
	
	@GetMapping("read")
	public String readForm(@RequestParam(name="boardNum") int boardNum, Model model) {
		
		bs.viewCount();
		
		try {
			BoardDTO dto = bs.find(boardNum);
			model.addAttribute("board", dto);
			log.debug("게시글 읽기 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생] 게시글 읽기 실패..");
		}
		
		return "board/readForm";
	}
}
