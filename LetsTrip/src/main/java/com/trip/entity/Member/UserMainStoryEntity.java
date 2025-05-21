package com.trip.entity.Member;

import lombok.*;

import java.time.LocalDateTime;

import com.trip.constant.Member.MainStoryNum;
import com.trip.entity.Lets.StoryEntity;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_main_story", uniqueConstraints= {@UniqueConstraint(columnNames= {"user_id", "mainstory_num"})})
public class UserMainStoryEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_main_story_id")
	private Long id;
	
	private Long userId;
	
	private Long storyId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="main_story_num", nullable=false)
	private MainStoryNum mainStoryNum;
	
}
