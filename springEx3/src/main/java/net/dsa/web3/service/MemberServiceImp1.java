package net.dsa.web3.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	
	
}
