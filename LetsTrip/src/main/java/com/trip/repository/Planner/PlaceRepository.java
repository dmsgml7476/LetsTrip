package com.trip.repository.Planner;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trip.entity.Planner.PlaceEntity;
import com.trip.entity.Planner.RegionEntity;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, Long>{
	
	@Query("SELECT p FROM PlaceEntity p JOIN FETCH p.region r " +
		       "WHERE p.category.categoryId = :categoryId AND r.regionId = :regionId AND p.placeName LIKE %:keyword%")
		Page<PlaceEntity> findByCategoryAndRegionAndKeyword(@Param("categoryId") Long categoryId,
		                                                    @Param("regionId") Long regionId,
		                                                    @Param("keyword") String keyword,
		                                                    Pageable pageable);
	
	@Query("SELECT p FROM PlaceEntity p JOIN FETCH p.region r " +
		       "WHERE p.category.categoryId = :categoryId AND r.upperRegion = :upperRegion AND p.placeName LIKE %:keyword%")
		Page<PlaceEntity> findByCategoryAndUpperRegionAndKeyword(@Param("categoryId") Long categoryId,
		                                                         @Param("upperRegion") String upperRegion,
		                                                         @Param("keyword") String keyword,
		                                                         Pageable pageable);


	List<PlaceEntity> findByRegion(RegionEntity region);

	
	@Query("SELECT p FROM PlaceEntity p JOIN FETCH p.region r " +
		       "WHERE p.category.categoryId = :categoryId AND p.placeName LIKE %:keyword%")
		Page<PlaceEntity> findByCategoryAndKeyword(@Param("categoryId") Long categoryId,
		                                           @Param("keyword") String keyword,
		                                           Pageable pageable);
	
	@Query("SELECT p FROM PlaceEntity p JOIN FETCH p.region r " +
		       "WHERE p.category.categoryId = :categoryId")
		Page<PlaceEntity> findByCategoryIdWithRegion(@Param("categoryId") Long categoryId,
		                                             Pageable pageable);

	@Query("SELECT p FROM PlaceEntity p JOIN FETCH p.region r " +
		       "WHERE p.category.categoryId = :categoryId AND r.regionId = :regionId")
		Page<PlaceEntity> findByCategoryAndRegion(@Param("categoryId") Long categoryId,
		                                          @Param("regionId") Long regionId,
		                                          Pageable pageable);

	@Query("SELECT p FROM PlaceEntity p JOIN FETCH p.region r " +
		       "WHERE p.category.categoryId = :categoryId AND r.upperRegion = :upperRegion")
		Page<PlaceEntity> findByCategoryAndUpperRegion(@Param("categoryId") Long categoryId,
		                                               @Param("upperRegion") String upperRegion,
		                                               Pageable pageable);
	
	Page<PlaceEntity> findByCategory_CategoryId(Long categoryId, Pageable pageable);
}
