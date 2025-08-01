package net.dsa.web6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 글 정보 DTO 
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	Integer num;
	String name;
	String comment;
}
