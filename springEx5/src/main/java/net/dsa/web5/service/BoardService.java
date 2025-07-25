package net.dsa.web5.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.BoardDTO;
import net.dsa.web5.dto.ReplyDTO;
import net.dsa.web5.entity.BoardEntity;
import net.dsa.web5.entity.MemberEntity;
import net.dsa.web5.entity.ReplyEntity;
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
	
	/**
	 * 게시글 조회 
	 * @param boardNum 조회할 글 번호
	 * @param read 조회수를 증가시킬 것인지 여부
	 * @return 글 정보
	 */
	public BoardDTO getBoard(int boardNum, boolean read) {
		
		BoardEntity entity = br.findById(boardNum).orElseThrow(() -> new EntityNotFoundException());
		
		if(read) {
			entity.setViewCount(entity.getViewCount() + 1);
		}
		BoardDTO dto = BoardDTO.convertToBoardDTO(entity);
		
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		for (ReplyEntity replyEntity : entity.getReplyList()) {
			ReplyDTO replyDTO = ReplyDTO.convertToReplyDTO(replyEntity);
			replyDTOList.add(replyDTO);
		}
		dto.setReplyList(replyDTOList);
	
		return dto;
	}

	/**
	 * 파일 다운로드
	 * @param boardNum		글 번호
	 * @param response		응답 객체(HTTP 응답으로 파일 전송)
	 * @param uploadPath	파일 저장 경로
	 */
	public void download(int boardNum, HttpServletResponse response, String uploadPath) {

		BoardEntity boardEntity = br.findById(boardNum).orElseThrow(
				() -> new EntityNotFoundException("게시글이 없습니다.")
				);
		
		try {
			/*
			 	Content-Disposition : 브라우저에게 응답에 포함된 컨텐츠를 어떻게 처리해야 할 지를 지시하는 HTTP 헤더
			 	attachment : 브라우저가 해당 파일을 다운로드하도록 지시
			 	filename : 다운로드 창에 표시된 파일 이름 지정
			 	URLEncoder.encode메서드 : 파일 이름에 한글, 공백, 특수문자가 포함되어 있을 경우, 파일 이름을 인코딩하여 브라우저에서 올바르게 처리할 수 있도록 파일 이름을 인코딩
			 */
			response.setHeader("Content-Disposition", "arrachment;filename=" + URLEncoder.encode(boardEntity.getOriginalName(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// 저장된 파일 경로
		String fullPath = uploadPath + "/" + boardEntity.getFileName();
		
		// 서버의 파일을 읽기 위한 입력스트림
		FileInputStream filein = null;
		
		// 클라이언트에게 파일을 전송하기 위한 출력스트림
		ServletOutputStream fileout = null;
		
		try {
			filein = new FileInputStream(fullPath);
			fileout = response.getOutputStream();
			
			// 입력스트림의 데이터를 출력스트림으로 복사
			// 이 과정을 통해 파일 데이터가 브라우저로 전송됨
			FileCopyUtils.copy(filein, fileout);
			
			filein.close();
			fileout.close();
		} catch (IOException e) {
			log.debug("[예외 발생] 지정된 파일을 찾을 수 없습니다.");
		}
	}
	/**
	 * 게시글 추천
	 * @param boardNum
	 */
	public void like(int boardNum) {

		BoardEntity entity = br.findById(boardNum).orElseThrow(
					() -> new EntityNotFoundException("해당 번호의 글이 없습니다.")
				);
		entity.setLikeCount(entity.getLikeCount() + 1);
		
	}
	
	/**
	 * 게시글 수정 처리
	 * @param boardDTO		수정할 글 정보
	 * @param uploadPath	파일을 저장할 경호
	 * @param upload		첨부파일
	 * @exception
	 */

	public void update(BoardDTO boardDTO, String uploadPath, MultipartFile upload) throws Exception{
		
		BoardEntity boardEntity = br.findById(boardDTO.getBoardNum()).orElseThrow(
					() -> new EntityNotFoundException("게시글이 없습니다."));
		
		// DB에 저장된 게시글 작성자와 수정하려고 하는 로그인 사용자가 같은지를 비교
		if (!boardEntity.getMember().getMemberId().equals(boardDTO.getMemberId())) {
			throw new RuntimeException("수정 권한이 없습니다.");
		}
		
		// 전달된 정보 수정
		boardEntity.setTitle(boardDTO.getTitle());
		boardEntity.setContents(boardDTO.getContents());
		boardEntity.setUpdateDate(LocalDateTime.now());
		
		// 업로드된 파일이 있으면 기존 파일을 삭제하고 새로 저장
		if (upload != null && !upload.isEmpty()) {
			// 기존 파일이 있는지 체크
			if (boardEntity.getFileName() != null) {
				fileManager.deleteFile(uploadPath, boardEntity.getFileName()); 	// 첫번째 파라미터는 경로, 두번째 파라미터는 파일 이름
			}
			
			String fileName = fileManager.saveFile(uploadPath, upload);
			boardEntity.setFileName(fileName);
			boardEntity.setOriginalName(upload.getOriginalFilename());
		}
		// 업로드할 파일이 존재하지 않는 경우
		else {
			if (boardEntity.getFileName() != null) {
				fileManager.deleteFile(uploadPath, boardEntity.getFileName());
			}
			boardEntity.setFileName(null);
			boardEntity.setOriginalName(null);
		}
		
				
		
	}
	
	/**
	 * 게시글 삭제 처리
	 * @param boardNum		삭제할 글 번호
	 * @param uploadPath	삭제할 파일 경로
	 * @param loginId		로그인한 유저 아이디
	 */
	public void delete(int boardNum, String uploadPath, String loginId) throws Exception  {

		BoardEntity boardEntity = br.findById(boardNum).orElseThrow(
				() -> new EntityNotFoundException("게시글이 없습니다."));
		
		if(!boardEntity.getMember().getMemberId().equals(loginId)) {
			throw new RuntimeException("삭제 권한이 없습니다.");
		}
		
		if (boardEntity.getFileName() != null) {
			fileManager.deleteFile(uploadPath, boardEntity.getFileName());
		}
		
		br.delete(boardEntity);
	}
	
	/**
	 * 리플 저장
	 * @param replyDTO 	작성한 리플 정보
	 */
	public void replyWrite(ReplyDTO replyDTO) {

		MemberEntity memberEntity = mr.findById(replyDTO.getMemberId()).orElseThrow(
				() -> new EntityNotFoundException("사용자 아이디가 없습니다."));
		
		BoardEntity boardEntity = br.findById(replyDTO.getBoardNum()).orElseThrow(
				() -> new EntityNotFoundException("게시글이 없습니다."));
		
		ReplyEntity entity = ReplyEntity.builder()
				.member(memberEntity)
				.board(boardEntity)
				.contents(replyDTO.getContents())
				.build();
		rr.save(entity);
	}

	/**
	 * 리플 삭제
	 * @param replyNum	삭제할 리플 번호
	 * @param username	로그인한 아이디
	 */
	public void replyDelete(Integer replyNum, String username) {

		ReplyEntity replyEntity = rr.findById(replyNum).orElseThrow(
				() -> new EntityNotFoundException("리플이 없습니다.")
				);
		if(!replyEntity.getMember().getMemberId().equals(username)) {
			throw new RuntimeException("삭제 권한이 없습니다.");
		}
		
		rr.delete(replyEntity);
	}

	/**
	 * 검색 후 지정한 한 페이지 분량의 글 목록 조회
	 * @param page		  	현재 페이지
	 * @param pageSize		한 페이지당 글 수
	 * @param searchType	검색 대상(title, contents, id)
	 * @param searchWord	검색어
	 * @return 한 페이지의 글 목록
	 */
	public Page<BoardDTO> getList2(int page, int pageSize, String searchType, String searchWord) {
		
		// 내부적으로 0부터 시작
		page--;
		
		// 정렬 기준 생성
		Sort sort = Sort.by(
			Sort.Order.desc("boardNum"),
			Sort.Order.desc("createDate")
			);
		
		/*
		 	Pageable
		 		어떤 페이지를, 몇 개씩, 어떤 정렬로 가져올 것인지 정의하는 인터페이스
		 		Page 객체에 담길 데이터(페이지 내용)를 조회하기 위해 필요한
		 		페이징 조건을 담고 있는 인터페이스
		 		PageRequest.of(페이지 번호, 페이지 크기, 정렬 정보) 사용
		 */
		// 페이지 요청 객체 생성 (페이지 번호, 크기, 정렬 기준)
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		
		//검색 조건에 따른 데이터 조회
		Page<BoardEntity> entityPage;
		
		switch (searchType) {
			case "title":
				entityPage = br.findByTitleContaining(searchWord, pageable);
				break;
			case "contents":
				entityPage = br.findByContentsContaining(searchWord, pageable);
				break;
			case "id":
				entityPage = br.findByMember_MemberId(searchWord, pageable);
				break;
			case "all":
				// JPQL 사용
				entityPage = br.searchAll(searchWord, pageable);	//커스텀 메서드
				break;
			default:
				entityPage = br.findAll(pageable);
				break;
		}
		
		// Entity > DTO로 변환
		List<BoardDTO> boardDTOList = new ArrayList<>();
		for (BoardEntity entity : entityPage) {
			BoardDTO dto = BoardDTO.convertToBoardDTO(entity);
			boardDTOList.add(dto);
		}
		
		/*
		 	PageImpl<T>
		 		Page<T>의 구현체
		 		직접 만든 List<T>를 Page로 Wrapping
		 */
		Page<BoardDTO> boardDTOPage = new PageImpl<>(
				boardDTOList,					// 실제 데이터
				entityPage.getPageable(),		// 페이지 정보
				entityPage.getTotalElements()	// 전체 항목 수
				);
		
		// JPA 메서드 네이밍 테스트
		log.debug("JPA 메서드 네이밍 테스트 -----------------------------------");
		
		// ReplyRepository 에 메서드 네이밍 규칙으로 만든 메서드 정의.
		
		// 한 게시글의 리플목록 조회
		List<ReplyEntity> test1 = rr.findByBoard_BoardNum(1);
		for (ReplyEntity reply : test1) {
			log.debug("test1: 글번호-{}, 작성자-{}, 리플번호-{}, 내용-{}", 
					reply.getBoard().getBoardNum(), 
					reply.getMember().getMemberId(), 
					reply.getReplyNum(),
					reply.getContents());
		}
		
		// 특정 회원의 리플목록 조회
		List<ReplyEntity> test2 = rr.findByMember_MemberId("aaa");
		for (ReplyEntity reply : test2) {
			log.debug("test2: 글번호-{}, 작성자-{}, 리플번호-{}, 내용-{}", 
					reply.getBoard().getBoardNum(), 
					reply.getMember().getMemberId(), 
					reply.getReplyNum(),
					reply.getContents());
		}
		
		log.debug("------------------------------------------------------");
		
		
		return boardDTOPage;
	}
	
}
