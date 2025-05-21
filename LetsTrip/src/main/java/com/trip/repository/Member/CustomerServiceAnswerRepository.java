package com.trip.repository.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.entity.Member.CustomerServiceAnswerEntity;
import com.trip.entity.Member.CustomerServiceEntity;

@Repository
public interface CustomerServiceAnswerRepository extends JpaRepository<CustomerServiceAnswerEntity, Long>{

	CustomerServiceAnswerEntity findByCustomerService(CustomerServiceEntity cs);

}
