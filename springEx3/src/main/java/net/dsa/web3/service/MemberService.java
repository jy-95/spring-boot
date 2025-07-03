package net.dsa.web3.service;

import java.util.List;

import net.dsa.web3.dto.MemberDTO;

public interface MemberService {

	void insertData();

	MemberDTO selectData(String id);

	void updateData(MemberDTO m);

	boolean deleteData(String id);

	List<MemberDTO> selectAllData();

	void save(MemberDTO member);
	
	void save2(MemberDTO member);

	boolean loginCheck(String id, String pw);


}
