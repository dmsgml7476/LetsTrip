package com.trip.repository.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.entity.Member.UserMainStoryEntity;

@Repository
public interface UserMainStoryRepository extends JpaRepository<UserMainStoryEntity, Long>{

	List<UserMainStoryEntity> findByUserId(Long userId);

}
