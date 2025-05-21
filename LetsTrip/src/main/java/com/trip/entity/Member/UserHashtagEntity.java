package com.trip.entity.Member;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.trip.constant.Member.Role;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_hashtag")
public class UserHashtagEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_hashtags_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	private UserEntity user; 
	
	@ManyToOne
	@JoinColumn(name = "hashtags_id", nullable = false)
	private HashtagsEntity hashtags;
	
	public UserHashtagEntity(UserEntity user, HashtagsEntity hashtags) {
        this.user = user;
        this.hashtags = hashtags;
    }
	
	
}
