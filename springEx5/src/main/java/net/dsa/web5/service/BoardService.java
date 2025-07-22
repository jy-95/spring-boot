package net.dsa.web5.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.BoardDTO;
import net.dsa.web5.entity.BoardEntity;
import net.dsa.web5.entity.MemberEntity;
import net.dsa.web5.repository.BoardRepository;
import net.dsa.web5.repository.MemberRepository;
import net.dsa.web5.repository.ReplyRepository;
import net.dsa.web5.util.FileManager;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service

public class BoardService {

	
	private final MemberRepository mr;
	private final BoardRepository br;
	private final ReplyRepository rr;
	private final FileManager fileManager;
	
	
	/**
	 * 게시판 글 저장
	 * @param boardDTO
	 * @param uploadPath
	 * @param upload
	 * @throws IOException 
	 */
	public void write(BoardDTO boardDTO, String uploadPath, MultipartFile upload) throws IOException {

		// 글 작성자 정보 조회
		MemberEntity memberEntity = mr.findById(boardDTO.getMemberId())
										.orElseThrow(
												() -> new EntityNotFoundException("회원이 없습니다.")
												);
										
		// 게시글 저장
		BoardEntity entity = BoardEntity.builder().build();
		entity.setMember(memberEntity);
		entity.setTitle(boardDTO.getTitle());
		entity.setContents(boardDTO.getContents());
		
		// 첨부파일
		if (upload != null && !upload.isEmpty()) {
			String fileName;
			fileName = fileManager.saveFile(uploadPath, upload);
			entity.setFileName(fileName);
			entity.setOriginalName(upload.getOriginalFilename());
		}
		
		log.debug("저장되는 Entity: {}", entity);
		br.save(entity);
		
	}

	/**
	 * 게시글 전체 조회
	 * @return 글 목록
	 */
	public List<BoardDTO> selectAll() {
		
		Sort sort = Sort.by(Sort.Direction.DESC, "boardNum");
		
		List<BoardEntity> entityList = br.findAll(sort);
		List<BoardDTO> dtoList = new ArrayList<>();

		for (BoardEntity entity : entityList) {
			BoardDTO dto = BoardDTO.convertToBoardDTO(entity);
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	public BoardDTO find(int boardNum) {
		
		BoardEntity entity = br.findById(boardNum).orElseThrow(() -> new EntityNotFoundException());
		
		int viewCount = entity.getViewCount();
		viewCount++;
		entity.setViewCount(viewCount);
		
		BoardDTO dto = BoardDTO.convertToBoardDTO(entity);
	
		return dto;
	}

	
	
}
