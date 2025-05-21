package com.trip.repository.Lets;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Repository;

import com.trip.entity.Lets.StoryEntity;
import com.trip.entity.Member.UserEntity;
@Repository
public interface StoryRepository extends JpaRepository<StoryEntity,Long> {
	
	// 공개 스토리 중 좋아요 수 많은 순 (비로그인용)
	@Query("""
		    SELECT s FROM StoryEntity s
		    JOIN FETCH s.user
		    LEFT JOIN LikeEntity l ON s = l.story
		    WHERE s.openArea = com.trip.enumType.OpenArea.PUBLIC
		    GROUP BY s
		    ORDER BY COUNT(l) DESC, s.writeAt DESC
		""")
		List<StoryEntity> findTop8PublicStoriesByLikes(Pageable pageable);

    // 특정 유저 ID 리스트의 공개 스토리 중 좋아요 수 많은 순 (로그인용)
    @Query("""
        SELECT s FROM StoryEntity s
        LEFT JOIN LikeEntity l ON s = l.story
        WHERE s.openArea = com.trip.enumType.OpenArea.PUBLIC
          AND s.user.id IN :userIds
        GROUP BY s
        ORDER BY COUNT(l) DESC, s.writeAt DESC
        """)
    List<StoryEntity> findTop8PublicStoriesByUserIdsOrderByLikes(@Param("userIds") List<Long> userIds, Pageable pageable);

    //  이미 포함된 스토리를 제외한 인기 스토리
    @Query("SELECT s FROM StoryEntity s WHERE s.openArea = 'PUBLIC' AND s.storyId NOT IN :excludeIds ORDER BY s.likes DESC")
    List<StoryEntity> findTopPublicStoriesExcludingIds(@Param("excludeIds") List<Long> excludeIds, Pageable pageable);

    
    // storyIds 리스트에 포함된 모든 스토리를 가져오는 쿼리문(likes찍은 스토리)
    @Query("SELECT s FROM StoryEntity s WHERE s.id IN :storyIds")
    List<StoryEntity> findAllByIdIn(@Param("storyIds") List<Long> storyIds);

	List<StoryEntity> findTop5ByOrderByWriteAtDesc();

	List<StoryEntity> findTop2ByOrderByWriteAtDesc();

	List<StoryEntity> findTop2ByOrderByLikes();

	StoryEntity findByUserId(UserEntity userEntity);

	List<StoryEntity> findAllByUserId(Long id);

	StoryEntity findByStoryId(Long storyId);
}
