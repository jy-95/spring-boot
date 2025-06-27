package net.dsa.web2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.dsa.web2.dto.Member;

@Service
public class MemberService {

		private List<Member> memberList = new ArrayList<>();
		
		public boolean save(Member member) {
			
			for (Member m : memberList) {
				if(m.getId().equals(member.getId())) {
					return false;
				}
			}
	
			memberList.add(member);
			return true;			
		}
		
		
		public List<Member> getList(){
			return this.memberList;
		}
		
		
		public boolean loginCheck(String id, String pw) {
			
			if(memberList.isEmpty()) {
				return false;
			}
			for (Member m : memberList) {
				if(id.equals(m.getId()) && pw.equals(m.getPw())) {
					return true;
				}
			}
			
			return false;
		}
		
}
