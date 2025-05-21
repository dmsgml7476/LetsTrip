package com.trip.repository.Member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.entity.Member.HashtagsEntity;
import com.trip.entity.Member.UserEntity;

@Repository
public interface HashtagsRepository extends JpaRepository<HashtagsEntity, Long>{
	
    List<HashtagsEntity> findByIsMbtiFalse();


    List<HashtagsEntity> findByIsMbtiTrue();


    Optional<HashtagsEntity> findByHashtag(String hashtag);

}
