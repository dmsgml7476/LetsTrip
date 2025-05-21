package com.trip.dto.Lets;

import lombok.Data;

@Data
public class StoryImgUrl {
			private String url;
			// 스토리 상세 페이지로 제공을 위한 pk값
			private Long storyId;
}
