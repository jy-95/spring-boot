package net.dsa.web5.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
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
	/**
	 * 회원목록 조회 처리
	 * @param userId
	 * @return 회원목록
	 */
	public List<MemberDTO> selectAll() {
		
		Sort sort = Sort.by(
				Sort.Order.asc("rolename"),
				Sort.Order.asc("memberName")
				
		);
		
		List<MemberEntity> memberList = mr.findAll(sort);
		List<MemberDTO> dtoList = new ArrayList<>();
		
		for (MemberEntity member : memberList) {
			MemberDTO dto = MemberDTO.builder().build();
			
			dto.setMemberId(member.getMemberId());
			dto.setMemberName(member.getMemberName());
			dto.setEmail(member.getEmail());
			dto.setPhone(member.getPhone());
			dto.setAddress(member.getAddress());
			dto.setRolename(member.getRolename());
			dto.setEnabled(member.getEnabled());
			dtoList.add(dto);

		}
		
		return dtoList;
	}
	
	/**
	 * 권한 변경 처리
	 * @param id
	 */
	public void changeRole(String id) {

		MemberEntity entity = mr.findById(id).orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
		
		String updateRoleName = entity.getRolename().equals("ROLE_USER") ? "ROLE_ADMIN" : "ROLE_USER";
		
		entity.setRolename(updateRoleName);
		
	}
	
	/**
	 * 계정의 상태를 활성화 / 비활성화
	 * @param id
	 * @param enabled
	 */
	public void changeEnabled(String id, boolean enabled) {
		
		MemberEntity entity = mr.findById(id).orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
		
		entity.setEnabled(enabled);
	}
	
}
