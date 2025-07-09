package net.dsa.web4.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web4.dto.GuestBookDTO;
import net.dsa.web4.entity.GuestBookEntity;
import net.dsa.web4.repository.GuestRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GuestService {

	private final GuestRepository repository;

	public void save(GuestBookDTO dto) {

		GuestBookEntity entity = GuestBookEntity.builder().name(dto.getName()).password(dto.getPassword())
				.message(dto.getMessage()).build();
		repository.save(entity);

	}

	public GuestBookDTO find(int num) {

		// 글번호에 일치하는 방명록을 가져오는 메서드(예외 발생 시 rollback!)
		GuestBookEntity entity = repository.findById(num).orElseThrow(() -> new EntityNotFoundException());

		// GuestBookDTO 타입의 빈 객체 생성( new GuestBook() 와 동일 )
		GuestBookDTO dto = GuestBookDTO.builder().build();

		// Entity 에서 DTO 로 데이터 옮겨 담기
		GuestBookDTO.convertEntity_to_DTO(entity, dto);

		// Controller로 dto return
		return dto;
	}
	/**
	 * 글 목록 조회
	 * @return 저장된 글 정보
	 */
	public List<GuestBookDTO> selectAll() {
		
		/*
		 	Spring Data JPA - Sort 객체
		 	데이터 조회 시 정렬(Order by)을 손쉽게 적용할 수 있도록
		 	해주는 유틸리티 클래스
		 	Sort 클래스는 정렬 기준을 지정하고, 이를 기반으로 
		 	쿼리를 수행할 때 결과를 정렬함.
		 	
		 	enum
		 		미리 정의된 고정 값들의 목록을 나타내는 자료형(상수집합)
		 		클래스처럼 사용 가능
		 		안정성, 코드 가독성, 타입체크..
		 */
		// 정렬 조건이 한 개 일때
		Sort sort = Sort.by(Sort.Direction.DESC, "num");
							//정렬 조건			 정렬 기준 대상(entity에 정의된 이름을 적어야 함)
		
		// 정렬 조건이 여러 개 일때 (1순위 날짜, 2순위 글번호, 3순위 작성자 이름)
		Sort sort2 = Sort.by(
			Sort.Order.desc("inputdate"),
			Sort.Order.desc("num"),
			Sort.Order.asc("name")
		);

		List<GuestBookEntity> entityList = repository.findAll(sort);
		List<GuestBookDTO> dtoList = new ArrayList<>();

		// 반복문을 통한 Entity 에서 DTO 로 데이터 옮겨 담기
		for (GuestBookEntity entity : entityList) {
			GuestBookDTO dto = GuestBookDTO.builder()
							   .num(entity.getNum())
							   .name(entity.getName())
							   .message(entity.getMessage())
							   .inputdate(entity.getInputdate())
							   .build();
			dtoList.add(dto);
		}

		return dtoList;
	}
	/**
	 * 글 번호와 비밀번호를 전달받아 저장된 비밀번호가 일치하면 삭제
	 * @param guestbook (num 삭제할 글 번호, password 입력한 비밀번호)
	 */
	public void delete(GuestBookDTO guestbook) {

		GuestBookEntity entity = repository.findById(guestbook.getNum())
				 .orElseThrow(() -> new EntityNotFoundException("해당 글이 없습니다."));
		
		// 글 정보가 있으면 비밀번호 비교
		if(entity.getPassword().equals(guestbook.getPassword())) {
			repository.deleteById(guestbook.getNum());
		}else {
			throw new RuntimeException("비밀번호가 틀립니다.");
		}

	}
}
