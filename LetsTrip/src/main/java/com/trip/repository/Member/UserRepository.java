package com.trip.repository.Member;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trip.entity.Lets.StoryEntity;
import com.trip.entity.Member.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	public UserEntity findByLoginId(String loginId);
	
	
	boolean existsByLoginId(String loginId);


	public boolean existsByNickname(String nickname);
	
	@Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.userHashtags WHERE u.id = :userId")
	Optional<UserEntity> findWithHashtagsById(@Param("userId") Long userId);


}
