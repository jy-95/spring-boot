package net.dsa.web5.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dsa.web5.entity.BoardEntity;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

	private Integer boardNum;		//게시글 일련번호
	private String memberId;		//작성자 ID
	private String memberName;		//작성자 이름
	private String title;			//글 제목
	private String contents;		//글 내용
	private Integer viewCount;		//조회수
	private Integer likeCount;		//추천수
	private String originalName;	//첨부파일의 원래 이름
	private String fileName;		//첨부파일의 저장된 이름
	private LocalDateTime createDate;	//작성 시간
	private LocalDateTime updateDate;	//수정 시간
	private List<ReplyDTO> replyList;	//리플 목록
	
	public static BoardDTO convertToBoardDTO(BoardEntity entity) {
		return BoardDTO.builder()
				.boardNum(entity.getBoardNum())
				.memberId(entity.getMember().getMemberId())
				.memberName(entity.getMember().getMemberName())
				.title(entity.getTitle())
				.contents(entity.getContents())
				.viewCount(entity.getViewCount())
				.likeCount(entity.getLikeCount())
				.originalName(entity.getOriginalName())
				.fileName(entity.getFileName())
				.createDate(entity.getCreateDate())
				.updateDate(entity.getUpdateDate())
				.build();
	}
}
