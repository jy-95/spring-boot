package net.dsa.web5.controller;

import java.util.List;

import org.hibernate.sql.ast.tree.from.QueryPartTableGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.BoardDTO;
import net.dsa.web5.dto.ReplyDTO;
import net.dsa.web5.service.BoardService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
@Controller
public class BoardController {

	private final BoardService bs;
	
	@Value("${board.uploadPath}")
	String uploadPath;			// 첨부파일 저장 경로
	
	@Value("${board.pageSize}")
	int pageSize;				// 페이지당 글 수
	
	@Value("${board.linkSize}")
	int linkSize;				// 페이지 이동 링크 수(좌우로)
	
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

/*	@GetMapping("list")
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
*/	
	/**
	 * 게시판 목록을 조회하고 페이징 및 검색 기능을 제공
	 * @param Model
	 * @param page			현재 페이지(default: 1)
	 * @param searchType	검색 대상(default: "")
	 * @param searchWord	검색어(default: "")
	 */
	@GetMapping("list")
	public String list(
			Model model,
			@RequestParam(name="page", defaultValue="1") int page,
			@RequestParam(name="searchType", defaultValue="") String searchType,
			@RequestParam(name="searchWord", defaultValue="") String searchWord
			){
		
		log.debug("properties 값: pageSize={}, linkSize={}, uploadPath={}", pageSize, linkSize, uploadPath);
		log.debug("요청 파라미터: page={}, searchType={}, searchWord={}", page, searchType, searchWord);
		
		/*
		 	Page<T>
		 		JPA에서 페이징과 관련된 작업을 간편하게 처리하기 위한 인터페이스
		 		조회된 데이터 목록 + 페이징 메타정보를 함께 담고 있는 객체
		 		List<T>처럼 데이터를 담고 있으면서도, 페이징 관련 정보 제공
		 */
		Page<BoardDTO> boardPage = bs.getList2(page, pageSize, searchType, searchWord);
		
		log.debug("목록 정보: {}", boardPage.getContent());
		log.debug("현재 페이지: {}", boardPage.getNumber());
		log.debug("전체 개수: {}", boardPage.getTotalElements());
		log.debug("전체 페이지 수: {}", boardPage.getTotalPages());
		log.debug("한 페이지 당 글 수: {}", boardPage.getSize());
		log.debug("이전페이지 존재 여부: {}", boardPage.hasPrevious());
		log.debug("다음페이지 존재 여부: {}", boardPage.hasNext());
		
		model.addAttribute("boardPage", boardPage); 	//출력할 글 정보
		model.addAttribute("page", page);				// 현재 페이지
		model.addAttribute("linkSize", linkSize);		// 페이지 이동링크 수
		model.addAttribute("searchType", searchType);	// 검색기준
		model.addAttribute("searchWord", searchWord);	// 검색어
		
		return "board/list";
			}
	
	
	/**
	 * 게시글 상세보기
	 * @param boardNum
	 * @param model
	 * @return
	 */
	@GetMapping("read")
	public String readForm(@RequestParam(name="boardNum", defaultValue = "0") int boardNum, Model model) {
		
		try {
			BoardDTO dto = bs.getBoard(boardNum, true);
			model.addAttribute("board", dto);
			log.debug("조회한 글 정보: {}", dto);
			return "board/read";
		} catch (Exception e) {
			log.debug("[예외 발생] 글 정보 조회 실패..");
			return "redirect:list";
		}
		
	}
	
	/** 첨부 파일 다운로드
	 * @param boardNum 게시글 번호
	 * @param response 응답 객체
	 */
	
	@GetMapping("download")
	public void download(@RequestParam("boardNum") int boardNum, HttpServletResponse response)
		{
			try {
				bs.download(boardNum, response, uploadPath);
				log.debug("다운로드 성공!");
			} catch (Exception e) {
				log.debug("[예외 발생] 다운로드 실패..");
			}
		}
	
	/**
	 * 게시글 추천
	 * @param boardNum	추천한 글 번호
	 * @return 글 읽기 페이지
	 */
	@GetMapping("like")
	public String like(@RequestParam("boardNum") int boardNum) {
		
		
		try {
			bs.like(boardNum);
			log.debug("추천 성공!");
			return "redirect:/board/read?boardNum=" + boardNum;
		} catch (Exception e) {
			log.debug("[예외 발생] 추천 실패..");
			return "redirect:list";
		}
		
	}
	
	/**
	 * 게시글 수정 폼으로 이동
	 * @param boardNum 	수정할 글 번호
	 * @param user		로그인한 사용자의 정보
	 * @param Model
	 * @return updateForm.html
	 */
	
	@GetMapping("update")
	public String update(@RequestParam("boardNum") int boardNum, @AuthenticationPrincipal UserDetails user, Model model) {
		try {
			BoardDTO boardDTO = bs.getBoard(boardNum, false);
			if (!user.getUsername().equals(boardDTO.getMemberId())) {
				throw new RuntimeException("수정 권한이 없습니다.");
			}
			model.addAttribute("board", boardDTO);
			return "board/updateForm";
		} catch (Exception e) {
			log.debug("[예외 발생] {}", e.getMessage());
			return "redirect:list";
		}
	}
	
	/**
	 * 게시글 수정 처리
	 * @param boardDTO 	수정할 글 정보
	 * @param user 		로그인한 사용자 정보
	 * @param upload 	업로드된 파일
	 * @return 			글 읽기 페이지
	 */
	
	@PostMapping("update")
	public String update(
			BoardDTO boardDTO, @AuthenticationPrincipal UserDetails user, @RequestParam(name = "upload", required = false) MultipartFile upload
			) {
		boardDTO.setMemberId(user.getUsername());
		
		try {
			bs.update(boardDTO, uploadPath, upload);
			log.debug("수정 성공!");
			return "redirect:read?boardNum=" + boardDTO.getBoardNum();
		} catch (Exception e) {
			log.debug("[예외 발생] {}", e.getMessage());
			return "redirect:list";
		}
	}
	
	/**
	 * 게시글 삭제
	 * @param boardNum		삭제할 글 번호
	 * @param user			로그인한 사용자 정보
	 * @return				글 목록
	 */
	@GetMapping("delete")
	public String delete(@RequestParam("boardNum") int boardNum, @AuthenticationPrincipal UserDetails user) {
		try {
			bs.delete(boardNum, uploadPath, user.getUsername());
			log.debug("삭제 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생]", e.getMessage());
		}
		return "redirect:list";
	}
	
	/**
	 * 리플 저장
	 * @param replyDTO		저장할 리플 정보
	 * @param user			로그인 사용자 정보
	 * @return 게시글 읽기 페이지로 이동
	 */
	@PostMapping("replyWrite")
	public String replyWrite(ReplyDTO replyDTO, @AuthenticationPrincipal UserDetails user) {
		
		try {
			replyDTO.setMemberId(user.getUsername());
			bs.replyWrite(replyDTO);
			log.debug("댓글 작성 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생] {}", e.getMessage());
		}
		return "redirect:read?boardNum=" + replyDTO.getBoardNum();
	}
	
	/**
	 * 리플 삭제
	 * @param replyDTO 	삭제할 리플 번호와 본문 글 번호
	 * @param user		로그인한 사용자 정보
	 * @return 	게시글 읽기 페이지
	 */
	@GetMapping("replyDelete")
	public String replyDelete(
			ReplyDTO replyDTO, @AuthenticationPrincipal UserDetails user) {
		
		try {
			bs.replyDelete(replyDTO.getReplyNum(), user.getUsername());
			log.debug("댓글 삭제 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생] {}", e.getMessage());
		}
		
		return "redirect:read?boardNum=" + replyDTO.getBoardNum();
	}
}
