package com.trip.dto.Planner;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class PlaceSelectDto {
	 private Long placeId;
	 private String placeName;
	 private Long categoryId;
	 
	 public PlaceSelectDto() {}

	public PlaceSelectDto(Long placeId, String placeName, Long categoryId) {
	        this.placeId = placeId;
	        this.placeName = placeName;
	        this.categoryId = categoryId;
	}
}
