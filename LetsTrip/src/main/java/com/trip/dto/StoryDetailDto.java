package com.trip.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.trip.dto.Lets.StoryCommentDto;

import lombok.Data;

@Data
public class StoryDetailDto {
	private Long id;
    private String title;
    private String content;
    private String author;
    private int likes;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private List<StoryCommentDto> comments;
}
