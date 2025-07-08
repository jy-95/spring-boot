package net.dsa.web4.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dsa.web4.entity.GuestBookEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookDTO {

	Integer num;				// 글 번호
	String name;				// 작성자 이름
	String password;			// 비밀번호
	String message;				// 게시글 내용
	LocalDateTime inputdate;	// 작성 시간
	
	public static void convertEntity_to_DTO(GuestBookEntity entity, GuestBookDTO dto) {
		dto.setNum(entity.getNum());
		dto.setName(entity.getName());
		dto.setPassword(entity.getPassword());
		dto.setMessage(entity.getMessage());
		dto.setInputdate(entity.getInputdate());
	}
	
	public static void convertDTO_to_Entity(GuestBookDTO dto, GuestBookEntity entity) {
		entity.setNum(dto.getNum());
		entity.setName(dto.getName());
		entity.setPassword(dto.getPassword());
		entity.setMessage(dto.getMessage());
		entity.setInputdate(dto.getInputdate());
		
		
}
	
}
