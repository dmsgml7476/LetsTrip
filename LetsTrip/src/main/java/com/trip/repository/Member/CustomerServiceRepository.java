package com.trip.repository.Member;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.entity.Member.CustomerServiceEntity;
import com.trip.entity.Member.UserEntity;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerServiceEntity, Long>{
	
	List<CustomerServiceEntity> findByUserId(Long userId);
	
	List<CustomerServiceEntity> findAllByOrderByQuestionTimeDesc();
	
	List<CustomerServiceEntity> findByUser_LoginIdContainingIgnoreCaseOrderByQuestionTimeDesc(String loginId);
	
	Page<CustomerServiceEntity> findByUser_LoginIdContainingIgnoreCase(String loginId, Pageable pageable);
	
	Page<CustomerServiceEntity> findByUserId(Long userId, Pageable pageable);

}
