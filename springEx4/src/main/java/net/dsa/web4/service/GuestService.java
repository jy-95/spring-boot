package net.dsa.web4.service;

import java.util.ArrayList;
import java.util.List;

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

		GuestBookEntity entity = GuestBookEntity.builder()
								 .name(dto.getName())
								 .password(dto.getPassword())
								 .message(dto.getMessage())
								 .build();
		repository.save(entity);
		
	}
	
	public GuestBookDTO find(int num) {	
		
		// 글번호에 일치하는 방명록을 가져오는 메서드(예외 발생 시 rollback!)
		GuestBookEntity entity = repository.findById(num)
								 .orElseThrow(() -> new EntityNotFoundException());
		
		// GuestBookDTO 타입의 빈 객체 생성( new GuestBook() 와 동일 )
		GuestBookDTO dto = GuestBookDTO.builder().build();
		
		// Entity 에서 DTO 로 데이터 옮겨 담기
		GuestBookDTO.convertEntity_to_DTO(entity, dto);
		
		// Controller로 dto return
		return dto;
	}
	
	public List<GuestBookDTO> selectAll() {
		
		// JPA의 Select * 기능의 메서드로부터 List Collection에 학생정보 담기
		List<GuestBookEntity> entityList = repository.findAll();
		// Controller로 return 해주기 위한 DTO 타입의 List 객체 생성
		List<GuestBookDTO>    dtoList    = new ArrayList<>();
		
		// 반복문을 통한 Entity 에서 DTO 로 데이터 옮겨 담기
		for (GuestBookEntity entity : entityList) {
			GuestBookDTO dto = GuestBookDTO.builder().build();
			GuestBookDTO.convertEntity_to_DTO(entity, dto);
			dtoList.add(dto);
		}
		
		// Controller로 학생목록 return
		return dtoList;
	}
	
}
