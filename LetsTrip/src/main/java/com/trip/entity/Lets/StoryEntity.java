package com.trip.entity.Lets;

import java.time.LocalDateTime;

import com.trip.entity.Member.UserEntity;
import com.trip.enumType.LocationInfo;
import com.trip.enumType.OpenArea;
import com.trip.enumType.StoryCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long storyId;
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="user_id", nullable=false)
		private UserEntity user;
	
		@Enumerated(EnumType.STRING)
		@Column(nullable = false)
		private StoryCategory storyCate;
		
		@Column(length = 50, nullable = false)
		private String storyTitle;
		
		@Column(length = 255)
		private String storyContent;
		
		@Enumerated(EnumType.STRING)
		private LocationInfo locationInfo;
		
		 @Column(length = 500)
		private String imageUrl;
		
		@Enumerated(EnumType.STRING)
	    @Column(nullable = false)
		private OpenArea openArea;
		
		private LocalDateTime writeAt;
		
		@Column(nullable=false)
		private int likes = 0;
		
}
