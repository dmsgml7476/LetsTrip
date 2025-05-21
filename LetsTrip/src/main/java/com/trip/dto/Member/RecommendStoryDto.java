package com.trip.dto.Member;

import java.time.LocalDateTime;

import com.trip.entity.Lets.StoryEntity;
import com.trip.entity.Member.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecommendStoryDto {

	private Long storyId;
	private String storyTitle;
	private LocalDateTime writeAt;
	private int likes; 
	
	private String imageUrl;
	private String nickname;
	
	
	public static RecommendStoryDto from(StoryEntity story) {
		RecommendStoryDto recommendStoryDto = new RecommendStoryDto();
		
		recommendStoryDto.setImageUrl(story.getImageUrl());
		recommendStoryDto.setNickname(story.getUser().getNickname());
		recommendStoryDto.setStoryId(story.getStoryId());
		recommendStoryDto.setStoryTitle(story.getStoryTitle());
		recommendStoryDto.setWriteAt(story.getWriteAt());
		recommendStoryDto.setLikes(story.getLikes());
		
		return recommendStoryDto;
	}
	
}
