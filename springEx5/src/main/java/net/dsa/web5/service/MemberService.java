package net.dsa.web5.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.MemberDTO;
import net.dsa.web5.entity.MemberEntity;
import net.dsa.web5.repository.MemberRepository;

/**
 * 회원정보 관련 처리 서비스
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

	// 회원정보 DB 작성
	private final MemberRepository mr;
	
	// 암호화
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * 가입시 아이디 중복 확인
	 * @param searchId	조회할 아이디
	 * @return 해당 아이디로 가입 가능 여부 true/false
	 */
	public boolean idCheck(String searchId) {
		
		return !mr.existsById(searchId); //일치하는 true 회원이 있으면 해당 아이디가 이미 사용중이라는 것. 따라서 !를 붙여서 반대 경우로 유도한다.
	}

	/**
	 * 가입 처리
	 * @param memberDTO
	 */
	public void join(MemberDTO dto) {
		
		MemberEntity entity = MemberEntity.builder()
				.memberId(dto.getMemberId())
				.memberPassword(
						passwordEncoder.encode(dto.getMemberPassword())
				)
				.memberName(dto.getMemberName())
				.email(dto.getEmail())
				.phone(dto.getPhone())
				.address(dto.getAddress())
				.enabled(true)
				.rolename("ROLE_USER")
				.build();
		
		mr.save(entity);
		
	}
	
	
}
