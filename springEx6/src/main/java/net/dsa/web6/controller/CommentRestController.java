package net.dsa.web6.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web6.dto.CommentDTO;
import net.dsa.web6.service.CommentService;

/**
 * 글 정보 관련 Ajax 요청 처리 컨트롤러
 */
// 비동기 요청의 응답으로 데이터를 전송하기 위한 메서드를 정의하는 컨트롤러
@RestController			//@ResponseBody를 포함하는 컨트롤러 어노테이션
@Slf4j
@RequestMapping("ex")
@RequiredArgsConstructor
public class CommentRestController {

	private final CommentService cs;
	/**
	 * 글 저장
	 * @param dto 저장할 글 정보
	 */
	@PostMapping("saveComment")
	public void saveComment(CommentDTO comment) {
		log.debug("저장할 글 정보: {}", comment);
		try {
			cs.save(comment);
			log.debug("> 글 저장 성공!");
		} catch (Exception e) {
			log.debug("> [예외 발생] {}", e.getMessage());
		}
	}
	
	/**
	 * 글 목록 조회
	 * @return 글 목록 
	 */
	@GetMapping("list")
	public List<CommentDTO> list(){
		
		List<CommentDTO> list = cs.getList();
		log.debug("> 글 목록: {}", list);
		
		return list;
	}
	
	/** 글 삭제
	 * @param num 삭제할 글 번호
	 */
	@DeleteMapping("delete/{num}")
	public void delete(
			@PathVariable("num") int num
			)	{
		log.debug("delete-num: {}", num);
		cs.delete(num);
	}
	
	/**
	 * 글 수정
	 * @param dto 수정할 글 정보
	 */
	@PatchMapping("update")
	public void update(
			@RequestBody CommentDTO dto) {
		log.debug("수정할 글 정보: {}", dto);
		
		try {
			cs.update(dto);
			log.debug("> 글 수정 성공!");
		} catch (Exception e) {
			log.debug("> [예외 발생] {}", e.getMessage());
		}
	}
}
