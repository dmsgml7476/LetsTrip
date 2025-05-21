package com.trip.repository.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trip.entity.Member.UserHashtagEntity;

@Repository
public interface UserHashtagRepository extends JpaRepository<UserHashtagEntity, Long>{

	// 특정 유저의 해시태그 ID 리스트
    @Query("SELECT uh.hashtags.id FROM UserHashtagEntity uh WHERE uh.user.id = :userId")
    List<Long> findHashtagIdsByUserId(@Param("userId") Long userId);

    // 같은 해시태그를 가진 유저 ID 리스트 (자기 자신은 제외)
    @Query("""
        SELECT DISTINCT uh.user.id 
        FROM UserHashtagEntity uh 
        WHERE uh.hashtags.id IN :hashtagIds 
          AND uh.user.id <> :userId
        """)
    List<Long> findUserIdsWithSameHashtags(@Param("hashtagIds") List<Long> hashtagIds, @Param("userId") Long userId);

	List<UserHashtagEntity> findAllByUserId(Long id);
}
