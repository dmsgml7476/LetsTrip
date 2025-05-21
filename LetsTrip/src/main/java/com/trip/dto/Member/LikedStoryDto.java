package com.trip.dto.Member;

import java.time.LocalDateTime;

import com.trip.entity.Lets.StoryEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikedStoryDto {

    private Long storyId;
    private String title;
    private String imageUrl;
    private String nickname;
    private String date;
    private String content;

    public static LikedStoryDto from(StoryEntity story) {
        LikedStoryDto dto = new LikedStoryDto();
        dto.setStoryId(story.getStoryId());
        dto.setTitle(story.getStoryTitle());
        dto.setImageUrl(story.getImageUrl());
        dto.setNickname(story.getUser().getNickname());
        dto.setDate(story.getWriteAt().toLocalDate().toString());
        dto.setContent(story.getStoryContent()); // 내용도 필요한 경우
        return dto;
    }
}