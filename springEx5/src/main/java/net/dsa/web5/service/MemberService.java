package net.dsa.web5.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	
	
}
