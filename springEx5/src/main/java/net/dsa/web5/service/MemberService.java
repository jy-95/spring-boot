package net.dsa.web5.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
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
	
	/**
	 * 회원정보 조회
	 * @param username 회원 아이디
	 * @return 회원정보
	 */
	 public MemberDTO getMemberInfo(String username) {
	     
		 MemberEntity entity = mr.findById(username).orElseThrow(() -> new EntityNotFoundException(username + ": 아이디가 없습니다."));
		 
		 MemberDTO dto = MemberDTO.builder()
		 		  				  .memberId(entity.getMemberId())
		 		  				  .memberName(entity.getMemberName())
		 		  				  .email(entity.getEmail())
		 		  				  .phone(entity.getPhone())
		 		  				  .address(entity.getAddress())
		 		  				  .build();
		 return dto;
	    }

	 
	/**
	 * 개인정보 수정 처리 
	 * @param dto 수정할 정보
	 */
	public void edit(MemberDTO dto) {
		
		MemberEntity entity = mr.findById(dto.getMemberId()).orElseThrow(() -> new EntityNotFoundException(
				dto.getMemberId() + ": 아이디가 없습니다."));
		
		// dto 비밀번호가 있으면 비밀번호도 수정
		
		if(!dto.getMemberPassword().isEmpty()) {
			entity.setMemberPassword(
					passwordEncoder.encode(dto.getMemberPassword()));
		}
		
		entity.setMemberName(dto.getMemberName());
		entity.setEmail(dto.getEmail());
		entity.setPhone(dto.getPhone());
		entity.setAddress(dto.getAddress());
		
		// @Transactional 안에서 필드를 변경하면 JPA가 변경을 감지해서 UPDATE를 실행
//		mr.save(entity);
		
	}

	public List<MemberDTO> selectAll(String userId) {
		
		List<MemberEntity> memberList = mr.findAll();
		List<MemberDTO> dtoList = new ArrayList<>();
		
		for (MemberEntity member : memberList) {
			if(!member.getMemberId().equals(userId)) {
			MemberDTO dto = MemberDTO.builder()
									 .memberId(member.getMemberId())
									 .memberName(member.getMemberName())
									 .email(member.getEmail())
									 .phone(member.getPhone())
									 .address(member.getAddress())
									 .rolename(member.getRolename())
									 .enabled(member.getEnabled())
									 .build();
			dtoList.add(dto);
			}
		}
		
		return dtoList;
	}

	public void changeRole(String id) {

		MemberEntity entity = mr.findById(id).orElseThrow(() -> new EntityNotFoundException(id + ": 아이디가 없습니다."));
		if(entity.getRolename().equals("ROLE_ADMIN")) {
			entity.setRolename("ROLE_USER");
		}else {
			entity.setRolename("ROLE_ADMIN");
		}
		
	}

	public void changeEnabled(String id) {
		MemberEntity entity = mr.findById(id).orElseThrow(() -> new EntityNotFoundException(id + ": 아이디가 없습니다."));
		if(entity.getEnabled().equals(true)) {
			entity.setEnabled(false);
		}else {
			entity.setEnabled(true);
		}
	}
	
}
