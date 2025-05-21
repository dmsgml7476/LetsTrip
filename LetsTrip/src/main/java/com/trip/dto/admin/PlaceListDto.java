package com.trip.dto.admin;

import com.trip.entity.Planner.PlaceEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaceListDto {

	private Long placeId;
	private String placeName;
	private String placeTel;
	private String upperRegion;
	private String regionName;
	private String placeAddress;   
	private String placeInfo;         

	private String categoryName;     

	public PlaceListDto(Long id, String placeName, String placeTel, String upperRegion,
	                    String regionName, String placeAddress, String placeInfo,
	                    String categoryName) {
		this.placeId = id;
		this.placeName = placeName;
		this.placeTel = placeTel;
		this.upperRegion = upperRegion;
		this.regionName = regionName;
		this.placeAddress = placeAddress;
		this.placeInfo = placeInfo;

		this.categoryName = categoryName;
	}

	public static PlaceListDto from(PlaceEntity entity) {
		return new PlaceListDto(
			entity.getPlaceId(),
			entity.getPlaceName(),
			entity.getPlaceTel(),
			entity.getRegion().getUpperRegion(),
			entity.getRegion().getRegionName(),
			entity.getPlaceAddress(),
			entity.getPlaceInfo(),

			entity.getCategory().getCategoryType() // 또는 .getCategoryType() 문자열로도 가능
		);
	}
}
