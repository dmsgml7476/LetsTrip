package com.trip.repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.entity.Member.VisiterLogEntity;

@Repository
public interface VisiterLogRepository extends JpaRepository<VisiterLogEntity, Long>{

}
