package com.trip.entity.Member;

import com.trip.dto.Member.UserAlertSettingDto;
import com.trip.dto.Member.UserSignUpDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user_alert_setting")
public class UserAlertSettingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_alert_setting_id")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private UserEntity user;
	
	@Column(name="comm_alert")
	private boolean commAlert;
	
	@Column(name="trip_alert")
	private boolean tripAlert;
	
	public static UserAlertSettingEntity from(UserSignUpDto dto, UserEntity user) {
		boolean agreed = dto.isAgreedAllAlerts();
        return UserAlertSettingEntity.builder()
                .user(user)
                .commAlert(agreed) 
                .tripAlert(agreed) 
                .build();
    }
	
	public void updateFromDto(UserAlertSettingDto dto) {
		this.commAlert = dto.isCommAlert();
		this.tripAlert = dto.isTripAlert();
	}

}
