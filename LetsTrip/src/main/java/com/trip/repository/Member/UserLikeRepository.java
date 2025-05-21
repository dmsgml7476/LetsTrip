package com.trip.repository.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trip.entity.Member.UserLikeEntity;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLikeEntity, Long>{
	@Query("SELECT u.storyId FROM UserLikeEntity u WHERE u.userId = :userId ORDER BY u.likeTime DESC")
    List<Long> findLikedStoryIdsByUserId(@Param("userId") Long userId);

	UserLikeEntity findByStoryIdAndUserId(Long storyId, Long id);
}
