package com.trip.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaceSaveDto {

	private String placeAddress;
	private String placeName;
	private String tel;
	private String placeInfo;
	private String searchableAddress;
	private String upperRegion;
	private Long regionId;
	private Integer categoryType;
	
}
