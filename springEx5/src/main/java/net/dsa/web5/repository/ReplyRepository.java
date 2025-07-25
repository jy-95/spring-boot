package net.dsa.web5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dsa.web5.entity.ReplyEntity;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer>{

	
	// 한 게시글의 리플 목록
	List<ReplyEntity> findByBoard_BoardNum(int boardNum);

	// 특정 회원의 리플 목록
	List<ReplyEntity> findByMember_MemberId(String memberId);

}
