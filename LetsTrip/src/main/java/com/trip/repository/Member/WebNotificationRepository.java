package com.trip.repository.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.constant.Member.NotificationType;
import com.trip.entity.Member.WebNotificationEntity;

@Repository
public interface WebNotificationRepository extends JpaRepository<WebNotificationEntity, Long>{
	int countByUserIdAndIsReadFalse(Long userId);
	
	int countByUserIdAndIsReadFalseAndTypeIn(Long userId, List<NotificationType> types);

	List<WebNotificationEntity> findTop5ByUserIdAndIsReadFalseAndTypeInOrderByCreateAtDesc(Long userId, List<NotificationType> types);
	
}
