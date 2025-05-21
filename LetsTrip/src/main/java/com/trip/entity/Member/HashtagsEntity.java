package com.trip.entity.Member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="hashtags")

public class HashtagsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="hashtags_id")
	private Long id;
	
	@Column(name="hashtag", unique=true, nullable=false)
	private String hashtag;
	
	
	@Column(name = "is_mbti", nullable = false)
	private Boolean isMbti = false;
	
}
