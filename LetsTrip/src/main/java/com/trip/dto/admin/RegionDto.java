package com.trip.dto.admin;

import com.trip.entity.Planner.RegionEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegionDto {

	private Long regionId;
	private String regionName;
	private String upperRegion;
	
	public static RegionDto from(RegionEntity entity) {
		 RegionDto dto = new RegionDto();
	        dto.regionId = entity.getRegionId();
	        dto.regionName = entity.getRegionName();
	        dto.upperRegion = entity.getUpperRegion();
	        return dto;
	}
}
