package com.trip.repository.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.trip.entity.Member.UserDetailEntity;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long>{

	Optional<UserDetailEntity> findByUserId(Long userId);
	boolean existsByEmail(String email);
	
	Optional<UserDetailEntity> findByEmail(String email);

}
