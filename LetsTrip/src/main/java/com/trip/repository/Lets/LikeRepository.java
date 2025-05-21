package com.trip.repository.Lets;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.trip.entity.Lets.LikeEntity;
@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long>{


}
