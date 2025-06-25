package net.dsa.ex2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.dsa.ex2.dto.Member;

@Service
public class MemberService {

	//회원목록
	private List<Member> memberList = new ArrayList<>();
}
