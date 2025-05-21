package com.trip.dto.Member;

import com.trip.entity.Member.UserAlertSettingEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAlertSettingDto {
	private boolean tripAlert = false;
    private boolean commAlert = false;

	public static UserAlertSettingDto fromEntity(UserAlertSettingEntity setting) {
		return UserAlertSettingDto.builder()
				.tripAlert(setting.isTripAlert())
				.commAlert(setting.isCommAlert())
				.build();
	}
}
