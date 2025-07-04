package net.dsa.web3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.StudentDTO;
import net.dsa.web3.entity.StudentEntity;
import net.dsa.web3.repository.StudentRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentRepository sr;

	
	public void save(StudentDTO s) {

		StudentEntity s3 = new StudentEntity();
		StudentDTO.convertDTO_to_Entity(s, s3);
		sr.save(s3);
	}

	
	public StudentDTO selectData(int id) {

		//해당 ID로 조회된 결과 출력, 없으면 null 출력
		StudentEntity member = sr.findById(id).orElse(null);
		if(member == null)
			return null;
		
		log.debug("[service-find] memberEntity : {}", member);
		
		// MemberDTO로 데이터 이동
		StudentDTO studentDTO = StudentDTO.builder()
							  .id(member.getStudentId())
							  .name(member.getName())
							  .major(member.getMajor())
							  .java(member.getJava())
							  .db(member.getDb())
							  .web(member.getWeb())
							  .build();
				
		return studentDTO;
	}

	public List<StudentDTO> selectAllData() {
			
			List<StudentEntity> entityList = sr.findAll();
			List<StudentDTO> dtoList = new ArrayList<>();
			
			for (StudentEntity entity : entityList) {
				StudentDTO dto = new StudentDTO();
				StudentDTO.convertEntity_to_DTO(entity, dto);
				dtoList.add(dto);
				
			}
			return dtoList;
	}
	
	public void updateData(StudentDTO s) {
		
		StudentEntity entity = sr.findById(s.getId())
				.orElseThrow(
					() -> new EntityNotFoundException("없는 ID")		//이 클래스는 @Transactional 예외가 발생 시 rollback한다.
				);
				
		// 수정할 정보를 entity에 세팅
		entity.setName(s.getName());
		entity.setMajor(s.getMajor());
		entity.setJava(s.getJava());
		entity.setDb(s.getDb());
		entity.setWeb(s.getWeb());
		
		sr.save(entity);
		
	}
	
	public boolean deleteData(int id) {
		
		boolean result = sr.existsById(id);
		
		if(result) {
			sr.deleteById(id);
		}
		
		return result;
		
	}
	
}
