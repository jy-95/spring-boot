package net.dsa.web5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원정보 DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

	String 	memberId;			// 사용자 아이디
	String 	memberPassword;		// 사용자 비밀번호
	String 	memberName;			// 이름
	String 	email;				// 이메일 주소
	String 	phone;				// 전화번호
	String 	address;			// 주소
	Boolean enabled;			// 계정상태(true : 정상, false : 사용불가)
	String 	rolename;			// 권한명(ROLE_USER:일반회원, ROLE_ADMIN:관리자)
}
