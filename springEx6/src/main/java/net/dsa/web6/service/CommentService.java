package net.dsa.web6.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web6.dto.CommentDTO;
import net.dsa.web6.entity.CommentEntity;
import net.dsa.web6.repository.CommentRepository;
/**
 * 글 정보 관련 서비스
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository cr;

	/**
	 * 글 저장
	 * @param dto
	 */
	public void save(CommentDTO comment) {

		CommentEntity entity = CommentEntity.builder().name(comment.getName())
							   .comment(comment.getComment())
							   .build();
		cr.save(entity);
		
	}

	/**
	 * 글 목록 조회
	 * @return 글 목록
	 */
	public List<CommentDTO> getList() {

		List<CommentEntity> entityList = cr.findAll();
		List<CommentDTO> 	dtoList	   = new ArrayList<>();
		
		for (CommentEntity entity : entityList) {
			CommentDTO dto = CommentDTO.builder()
							 .num(entity.getNum())
							 .name(entity.getName())
							 .comment(entity.getComment())
							 .build();
			dtoList.add(dto);
							 
		}
		
		return dtoList;
	}
	/**
	 * 글 삭제
	 * @param num
	 */
	public void delete(int num) {
		cr.deleteById(num);
	}

	/**
	 * 글 수정
	 * @param dto 수정할 글 내용
	 */
	public void update(CommentDTO dto) {
		CommentEntity entity = cr.findById(dto.getNum()).orElseThrow(
				() -> new EntityNotFoundException("수정할 댓글이 없습니다.")
				);
		entity.setComment(dto.getComment());
	}
}
