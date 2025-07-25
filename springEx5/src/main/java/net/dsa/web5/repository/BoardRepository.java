package net.dsa.web5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.dsa.web5.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

	// 제목을 기준으로 검색어로 검색한 후 지정한 한 페이지 분량 리턴
	Page<BoardEntity> findByTitleContaining(String searchWord, Pageable pageable);
	
	// 본문을 기준으로 검색어로 검색한 후 지정한 한 페이지 분량 리턴
	Page<BoardEntity> findByContentsContaining(String searchWord, Pageable pageable);

	// 아이디를 기준으로 검색어로 검색한 후 지정한 한 페이지 분량 리턴
	Page<BoardEntity> findByMember_MemberId(String searchWord, Pageable pageable);

	// 전체를 기준으로 검색어로 검색한 후 지정한 한 페이지 분량 리턴
	// JPQL 사용
	@Query("SELECT board " + "FROM BoardEntity board " + 
			"WHERE board.title 				LIKE %:searchWord% " + 
			"   OR board.contents 			LIKE %:searchWord% " +
			"	OR board.member.memberId	LIKE %:searchWord% " +
			"ORDER BY board.boardNum DESC"
			)
	
	Page<BoardEntity> searchAll(String searchWord, Pageable pageable);
	
	/*
	 	JPA 메서드 vs JPQL vs QueryDSL
	 	1. JPA 메서드 네이밍
	 		메서드 이름만으로 쿼리를 자동 생성하는 기능
	 		- 매우 간단하고 빠르게 구현 가능
	 		- 복잡한 조건은 네이밍이 길고 복잡해짐
	 		- 동적 쿼리에는 한계가 있음
	 		- 리턴 타입을 유연하게 지정 가능(Entity, Optional 등..)
	 	2. JPQL(Java Persistence Query Language)
	 		SQL과 비슷하지만 테이블이 아닌 엔티티 객체를 대상으로 작성하는 JPA의 객체지향 쿼리 언어
	 		- 동적 쿼리는 직접 문자열 조립 or @Query 사용
	 		- SQL과 비슷해서 쉽게 사용
	 		- 복잡한 쿼리 작성 가능
	 		- 문자열 기반이라 컴파일 타임에 오류 체크 불가(런타임 오류 위험)
	 		- 가독성이 떨어질 수 있음
	 	3. QueryDSL
	 		자바 코드로 타입 안정성(type-safe)을 보장하는 동적 쿼리 빌더
	 		- 코드 작성량이 많을 수는 있지만 유지보수가 편하다
	 		- IDE 자동완성 지원이 좋다
	 		- 복잡한 동적 조건과 조인, 서브쿼리 작성에 강력함
	 		- 추가 라이브러리 의존성 필요(gradle/maven 설정)
	 */

}
