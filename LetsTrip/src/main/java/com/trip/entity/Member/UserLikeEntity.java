package com.trip.entity.Member;

import java.time.LocalDateTime;

import com.trip.entity.Lets.StoryEntity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_like")
public class UserLikeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_like_id")
	private Long id;
	
	private Long userId;
	
	private Long storyId;
	
	private LocalDateTime likeTime;
	
	
	
}
