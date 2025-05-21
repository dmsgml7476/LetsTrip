package com.trip.entity.Member;

import java.time.LocalDateTime;

import com.trip.constant.Member.NotificationType;
import com.trip.entity.Lets.StoryEntity;
import com.trip.entity.Planner.TripPlanEntity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="web_notification")
public class WebNotificationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="web_notification_id")
	private Long id;
	
	private Long userId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="notification_type", nullable=false)
	private NotificationType type;
	
	@Column(nullable=false)
	private String message;
	
	private Long targetId;
	
	private boolean isRead;
	private LocalDateTime createAt;
	
	@PrePersist
	public void prePersist() {
	    if (this.createAt == null) {
	        this.createAt = LocalDateTime.now();
	    }
	}
	
	
}
