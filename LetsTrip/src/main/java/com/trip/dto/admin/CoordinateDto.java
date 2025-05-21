package com.trip.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CoordinateDto {

//	카카오 에서 받아온 위도/ 경도 받는 dto
	
	private float latitude;
	private float longitude;
}
