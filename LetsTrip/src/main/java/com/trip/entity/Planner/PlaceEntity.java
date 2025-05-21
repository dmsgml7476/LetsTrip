package com.trip.entity.Planner;

import java.time.LocalDate;

import com.trip.dto.admin.PlaceSaveDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long placeId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="region_id", nullable=false)
    private RegionEntity region;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
	private String placeName;
	private String placeAddress;
	private String placeInfo;
	private float placeLatitude;
	private float placeLongitude;
	private String placeTel;
	private LocalDate createDate;
	private LocalDate placeModifiedDate;
	
	
	public static PlaceEntity of(PlaceSaveDto dto, RegionEntity region, CategoryEntity category) {
		PlaceEntity place = new PlaceEntity();
		place.setPlaceName(dto.getPlaceName());
		place.setPlaceAddress(dto.getPlaceAddress());
		place.setPlaceTel(dto.getTel());
		place.setPlaceInfo(dto.getPlaceInfo());
		place.setRegion(region);
		place.setCategory(category);
		place.setCreateDate(LocalDate.now());
		place.setPlaceModifiedDate(LocalDate.now());
		return place;
	}
	
	public void updatePlace(PlaceSaveDto dto, RegionEntity region, CategoryEntity category) {
	    this.placeName = dto.getPlaceName();
	    this.placeAddress = dto.getPlaceAddress();
	    this.placeTel = dto.getTel();
	    this.placeInfo = dto.getPlaceInfo();
	    this.region = region;
	    this.category = category;
	    this.placeModifiedDate = LocalDate.now();
	}
	
}
