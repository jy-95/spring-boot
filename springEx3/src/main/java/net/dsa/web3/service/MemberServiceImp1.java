package net.dsa.web3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.MemberDTO;
import net.dsa.web3.entity.MemberEntity;
import net.dsa.web3.repository.MemberRepository;

@Service
@Slf4j
/*
 	@Transactional
 	  - 메서드나 클래스에 적용하면, 해당 메서드가 호출될 때 트랜잭션이 시작됨
 	  - 메서드가 정상적으로 완료되면 커밋(Commit)
 	  - 실행 중 예외가 발생하면 롤백(rollback)
 	  - 해당 어노테이션은 public 메서드에만 적용
 */
@Transactional
@RequiredArgsConstructor
public class MemberServiceImp1 implements MemberService{

	private final MemberRepository mr;

	@Override
	public void insertData() {
		// 일반적인 인스턴스 생성
		MemberEntity m1 = new MemberEntity("eee", "111", "홍길동", "010-1111-2222", "서울특별시 강남구");
		
		//builder 사용
		MemberEntity m2 = MemberEntity.builder().id("ddd").pw("123").name("고길동").phone("010-2222-3333").address("서울특별시 금천구").build();
		
		
		/*
		 	JpaRepository 기본 제공 메서드
		 	 save				Insert or Update
		 	 findById(id)		ID 기준 조회
		 	 findAll()			전체 조회
		 	 delete(entity)		엔티티 삭제
		 	 deleteById(id)		ID 기준 삭제
		 	 count()			전체 개수 조회
		 */
		mr.save(m1);
		log.debug("[service-save] memberEntity: {}", m1);
	}

	/**
	 * 회원정보 조회
	 * @param id 조회할 아이디
	 * @return 조회 결과를 담은 객체
	 */
	@Override
	public MemberDTO selectData(String id) {

		//해당 ID로 조회된 결과 출력, 없으면 null 출력
		MemberEntity member = mr.findById(id).orElse(null);
		if(member == null)
			return null;
		
		log.debug("[service-find] memberEntity : {}", member);
		
		// MemberDTO로 데이터 이동
		MemberDTO memberDTO = MemberDTO.builder()
							  .id(member.getId())
							  .pw(member.getPw())
							  .name(member.getName())
							  .phone(member.getPhone())
							  .address(member.getAddress())
							  .build();
				
		return memberDTO;
	}

	
	/**
	 * 회원정보 수정
	 * @param MemberDTO 회원정보를 담은 객체
	 */
	@Override
	public void updateData(MemberDTO m) {
		/*
		 	Optional<T>
		 	null 값으로 인한 NullPointerException을 방지하기 위한 자바 클래스
		 	 - null을 직접 쓰는 것보다 안정적이고 가독성이 높음
		 
		 */
		
		MemberEntity entity = mr.findById(m.getId())
				.orElseThrow(
					() -> new EntityNotFoundException("없는 ID")		//이 클래스는 @Transactional 예외가 발생 시 rollback한다.
				);
				
		// 수정할 정보를 entity에 세팅
		entity.setPw(m.getPw());
		entity.setName(m.getName());
		entity.setPhone(m.getPhone());
		entity.setAddress(m.getAddress());
		
		mr.save(entity);
		
	}

	/**
	 * 회원정보 삭제
	 * @param id 삭제할 아이디
	 * @return 삭제 여부 true / false
	 */
	@Override
	public boolean deleteData(String id) {
		
		// id에 일치하는 회원정보가 있는지 없는지를 true / false 리턴
		boolean result = mr.existsById(id);
		
		if(result) {
			mr.deleteById(id);  //id에 일치하는 회원정보 삭제
		}
		
		return result;
		
	}

	@Override
	public List<MemberDTO> selectAllData() {
		
		List<MemberEntity> entityList = mr.findAll();
		List<MemberDTO> dtoList = new ArrayList<>();
		
		for (MemberEntity entity : entityList) {
			MemberDTO dto = new MemberDTO();
			// dto.setID(entity.getId());
			MemberDTO.convertEntity_to_DTO(entity, dto);
			dtoList.add(dto);
			
		}
		return dtoList;
			
		
	}

	/**
	 * 회원가입 처리
	 * @param member 회원가입 정보
	 */
	@Override
	public void save(MemberDTO member) {
//		MemberEntity m3 =  MemberEntity.builder()
//						  .id(member.getId())
//						  .pw(member.getPw())
//						  .name(member.getName())
//						  .phone(member.getPhone())
//						  .address(member.getAddress())
//						  .build();
//		
		MemberEntity m3 = new MemberEntity();
		MemberDTO.convertDTO_to_Entity(member, m3);
		
		mr.save(m3);
		
	}
	
	/**
	 * convertDTO_to_Entity 함수를 수정해서 entity 값을 반환하게 한 뒤 간단하게 받은 진섭상.ver
	 */
	@Override
	public void save2(MemberDTO member) {
		
		mr.save(MemberDTO.convertDTO_to_Entity2(member));
		
	}
	
	

	
	
	
}
