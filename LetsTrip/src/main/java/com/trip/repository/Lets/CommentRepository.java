package com.trip.repository.Lets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Repository;


import com.trip.entity.Lets.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

}
