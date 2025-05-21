package com.trip.entity.Lets;

import java.time.LocalDateTime;

import com.trip.entity.Member.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommentEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
			private Long commentId;
	
	      @ManyToOne(fetch=FetchType.LAZY)
	      @JoinColumn(name="story_id", nullable=false)
			private StoryEntity story;
	      
	       @ManyToOne(fetch=FetchType.LAZY)
			@JoinColumn(name="user_id", nullable=false)
			private UserEntity user;
			
			@Column(length = 255, nullable = false)
			private String CommentContent;
			
			private LocalDateTime commentAt;
			
}
