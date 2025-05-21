package com.trip.dto.Planner;

import com.trip.entity.Planner.RegionEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionSelectDto {
	private Long regionId;
	private String regionName;
	private String upperRegionName;
	
	public static RegionSelectDto from(RegionEntity entity) {
		 
		RegionSelectDto dto = new RegionSelectDto();
		  dto.regionId = entity.getRegionId();
		  dto.regionName = entity.getRegionName();
		  dto.upperRegionName = entity.getUpperRegion();
		
		
		return dto;
	}
}
