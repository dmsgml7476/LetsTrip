package com.trip.dto.Lets;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StoryCommentDto {
			private Long id; // 댓글ID
			private String commentContent;
			private LocalDateTime commentAt;
			private String writer; 
			
			
}
