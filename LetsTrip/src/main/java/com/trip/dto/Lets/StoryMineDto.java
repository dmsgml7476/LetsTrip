package com.trip.dto.Lets;

import java.util.List;

import lombok.Data;

@Data
public class StoryMineDto {
	private String loginId; //작성자 로그인 아이디
	private Long storyId; //스토리글 PK
	private String storyTitle; //제목
	private String storyImgUrl; //이미지
	private String storyContent; // 내용
	private List<String> hashTags; // 해시태그 두개 이상이니까 배열
	private int likes;
	private boolean isLike;
	private List<StoryCommentDto> storyCommentDtoList; // 댓글 목록 

}
