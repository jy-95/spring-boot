package net.dsa.web3.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.MemberDTO;
import net.dsa.web3.entity.MemberEntity;
import net.dsa.web3.repository.MemberRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService {
	
	private final MemberRepository mr;
	
	@Override
	
	public void insertData() {
		
		MemberEntity m1 = new MemberEntity("eee", "111", "홍길동", "010-1111-2222", "서울특별시 강남구");
		MemberEntity m2 = MemberEntity.builder().id("ddd").pw("123").name("고길동").phone("010-2222-3333").address("서울특별시 금천구").build();
		
		mr.save(m1);
	}

	@Override
	public MemberDTO selectData(String id) {
		
		MemberEntity member = mr.findById(id).orElse(null);
		if(member == null)
			return null;
		MemberDTO memberDTO = MemberDTO.builder()
				  .id(member.getId())
				  .pw(member.getPw())
				  .name(member.getName())
				  .phone(member.getPhone())
				  .address(member.getAddress())
				  .build();
	
		return memberDTO;
	}

	
	
}
