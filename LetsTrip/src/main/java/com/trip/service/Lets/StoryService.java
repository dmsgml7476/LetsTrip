package com.trip.service.Lets;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import com.trip.control.Lets.Story;
import com.trip.dto.StoryDetailDto;
import com.trip.dto.Lets.BoardFormDto;
import com.trip.dto.Lets.StoryCardDto;
import com.trip.dto.Lets.StoryCommentDto;
import com.trip.dto.Lets.StoryImgUrl;
import com.trip.dto.Lets.StoryMineDto;
import com.trip.entity.Lets.CommentEntity;
import com.trip.entity.Lets.StoryEntity;
import com.trip.entity.Member.UserEntity;
import com.trip.entity.Member.UserHashtagEntity;
import com.trip.entity.Member.UserLikeEntity;
import com.trip.repository.Lets.CommentRepository;
import com.trip.repository.Lets.StoryRepository;
import com.trip.repository.Member.UserHashtagRepository;
import com.trip.repository.Member.UserLikeRepository;
import com.trip.repository.Member.UserMainStoryRepository;
import com.trip.repository.Member.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final UserMainStoryRepository userMainStoryRepository;
			private final StoryRepository storyRepository;
			
			private final UserHashtagRepository userHashtagRepository;
			private final UserLikeRepository userLikeRepository;
			private final UserRepository userRepository;
			private final CommentRepository commentRepository;
			
			// 댓글 저장용
			public void commentSave(StoryCommentDto storyCommentDto, Long storyId,String loginId) {
					StoryEntity storyEntity = storyRepository.findById(storyId).get();
					UserEntity userEntity = userRepository.findByLoginId(loginId);
					CommentEntity commentEntity = new CommentEntity();
					commentEntity.setStory(storyEntity);
					commentEntity.setUser(userEntity);
					commentEntity.setCommentContent(storyCommentDto.getCommentContent());
					commentEntity.setCommentAt(LocalDateTime.now());
					commentRepository.save(commentEntity);
			}
			
		
			// 스토리 목록 보여주기 위한 메서드 (최근 순, 공감 다수 순)
			public List<StoryCardDto> getStoryList(String loginId){
				
				// 로그인 했다면  로그인한 사람이 좋아요 누르거 표시 해야 되니까  로그인 한 유저의 id 가져오기 필요
				UserEntity userEntity =null;
				if( loginId!=null) {
					userEntity= userRepository.findByLoginId(loginId);
				}
				List<StoryEntity> storyList = storyRepository.findTop2ByOrderByWriteAtDesc(); //최근순
				List<StoryEntity>storyLike = storyRepository.findTop2ByOrderByLikes();//공감순
				storyList.addAll(storyLike);
				
				List<StoryCardDto> storyCardList = new ArrayList<>(); 
				
				for(StoryEntity story : storyList) {
					StoryCardDto storyCardDto = new StoryCardDto();
					
					// 로그인 했다면 그사람의 좋아요 가져오기 
					if(userEntity!=null) {
						UserLikeEntity userLikeEntity = userLikeRepository.findByStoryIdAndUserId(story.getStoryId(), userEntity.getId());
						storyCardDto.setLike( userLikeEntity==null ? false : true   );
					}
					
					List<UserHashtagEntity> userHashtagEntityList = userHashtagRepository.findAllByUserId(story.getUser().getId());
					if(  !userHashtagEntityList.isEmpty() ) { // 해시 태그 있으면 실행
						List<String> tempTag = new ArrayList<>(); // 모든 해시 태그 담아두기 위한 임시공간
						for( UserHashtagEntity userHashtagEntity : userHashtagEntityList) { // 해시 태그 갯수만큼 반복 그래야 모든 해시 태그 다가져올수 있음
							tempTag.add(userHashtagEntity.getHashtags().getHashtag()); // 해시 태그 하나씩 임시공간에 저장
						}
						// 임시공간에 저장된 해시 태그 DTO의 배열에 저장
						storyCardDto.setHashTags( tempTag.toArray(new String[0]));
					}
					storyCardDto.setLoginId(story.getUser().getLoginId());
					storyCardDto.setStoryId(story.getStoryId());
					storyCardDto.setStoryTitle(story.getStoryTitle());
					storyCardDto.setStoryContent(story.getStoryContent());
					storyCardDto.setStoryImgUrl(story.getImageUrl());
					storyCardDto.setStoryContent(story.getStoryContent());
					storyCardDto.setLikes(story.getLikes());
					
					System.out.println(  storyCardDto);
					storyCardList.add(storyCardDto);
				}
//				for(StoryEntity story : storyLike) {
//					StoryCardDto storyCardDto = new StoryCardDto();
//					storyCardDto.setStoryId(story.getStoryId());
//					storyCardDto.setStoryTitle(story.getStoryTitle());
//					storyCardDto.setStoryContent(story.getStoryContent());
//					storyCardDto.setStoryImgUrl(story.getImageUrl());
//					storyCardDto.setStoryContent(story.getStoryContent());
//					storyCardDto.setLikes(story.getLikes());
//					storyCardList.add(storyCardDto);
//				}
			
				return storyCardList;
			}
			
			// 커뮤니티 첫페이지에 스토리 이미지 목록 보여주기 
			public List<StoryImgUrl> getStoryImgUrls(){
				List<StoryImgUrl> storyImgUrls = new ArrayList<>();
				
				//스토리 테이블에서 최근 데이터 5개 가져오기
				List<StoryEntity> stories = storyRepository.findTop5ByOrderByWriteAtDesc();
				
				// entity -> dto
				for (StoryEntity story : stories) {
					StoryImgUrl storyImgUrl = new StoryImgUrl();
					storyImgUrl.setStoryId(story.getStoryId());
					storyImgUrl.setUrl(story.getImageUrl());
					storyImgUrls.add(storyImgUrl);
				}
				
				return storyImgUrls;
			}


			public void updateStoryContent(Long id, String content) {
				StoryEntity story = storyRepository.findById(id)
				        .orElseThrow(() -> new RuntimeException("스토리를 찾을 수 없습니다."));

				    story.setStoryContent(story.getStoryContent());
				    storyRepository.save(story);
				
			}


			public StoryCardDto getMyStory() {
				// TODO Auto-generated method stub
				return null;
			}


			public List<StoryMineDto> getStoriesByUser(String loginId) {
				UserEntity userEntity= userRepository.findByLoginId(loginId);
				List<StoryEntity> storyList = storyRepository.findAllByUserId(userEntity.getId());

			    List<StoryMineDto> storyMineDtoList = new ArrayList<>();

			    for (StoryEntity story : storyList) {
			        StoryMineDto dto = new StoryMineDto();

			        // 해시태그 조회
			        List<UserHashtagEntity> userHashtagEntityList = userHashtagRepository.findAllByUserId(story.getUser().getId());
			        if (!userHashtagEntityList.isEmpty()) {
			            List<String> tempTag = new ArrayList<>();
			            for (UserHashtagEntity userHashtagEntity : userHashtagEntityList) {
			                tempTag.add(userHashtagEntity.getHashtags().getHashtag());
			            }
			            dto.setHashTags(tempTag);
			        }

			        // DTO 세팅
			        dto.setLoginId(story.getUser().getLoginId());
			        dto.setStoryId(story.getStoryId());
			        dto.setStoryTitle(story.getStoryTitle());
			        dto.setStoryContent(story.getStoryContent());
			        dto.setStoryImgUrl(story.getImageUrl());
			        dto.setLikes(story.getLikes());

			        storyMineDtoList.add(dto);
			    }

			    return storyMineDtoList;
			}
			


			public Story findById(Long storyId) {
				// TODO Auto-generated method stub
				return null;
			}


			public void saveStory(@Valid BoardFormDto boardFormDto, String name) {
				UserEntity user = userRepository.findByLoginId(name);
					   

			    StoryEntity story = new StoryEntity();
			    story.setUser(user);
			    story.setStoryTitle(boardFormDto.getStoryTitle());
			    story.setStoryContent(boardFormDto.getStoryContent());
			    story.setStoryCate(boardFormDto.getStoryCate());
			    story.setOpenArea(boardFormDto.getOpenArea());
			    story.setLocationInfo(boardFormDto.getLocationInfo());
			    story.setWriteAt(LocalDateTime.now());

			    // 이미지 저장 처리
			    if (boardFormDto.getFile() != null && !boardFormDto.getFile().isEmpty()) {
			        String filename = UUID.randomUUID() + "_" + boardFormDto.getFile().getOriginalFilename();
			        String url = "/uploads/" + filename; // 실제 파일 저장 처리 생략 가능
			        story.setImageUrl(url);
			    }

			    // 링크 로그 (DB에 저장하지 않는다면)
			    if (boardFormDto.getLinkUrl() != null && !boardFormDto.getLinkUrl().isBlank()) {
			        System.out.println("입력된 링크: " + boardFormDto.getLinkUrl());
			    }

			    storyRepository.save(story);
			}


			public StoryCardDto getStoryDetail(Long storyId) {
				StoryEntity entity = storyRepository.findByStoryId(storyId);
		        
				StoryCardDto storyCardDto = new StoryCardDto();
				
				List<UserHashtagEntity> userHashtagEntityList = userHashtagRepository.findAllByUserId(entity.getUser().getId());
				if(  !userHashtagEntityList.isEmpty() ) { // 해시 태그 있으면 실행
					List<String> tempTag = new ArrayList<>(); // 모든 해시 태그 담아두기 위한 임시공간
					for( UserHashtagEntity userHashtagEntity : userHashtagEntityList) { // 해시 태그 갯수만큼 반복 그래야 모든 해시 태그 다가져올수 있음
						tempTag.add(userHashtagEntity.getHashtags().getHashtag()); // 해시 태그 하나씩 임시공간에 저장
					}
					// 임시공간에 저장된 해시 태그 DTO의 배열에 저장
					storyCardDto.setHashTags( tempTag.toArray(new String[0]));
				}
				storyCardDto.setLoginId(entity.getUser().getLoginId());
				storyCardDto.setStoryId(entity.getStoryId());
				storyCardDto.setStoryTitle(entity.getStoryTitle());
				storyCardDto.setStoryContent(entity.getStoryContent());
				storyCardDto.setStoryImgUrl(entity.getImageUrl());
				storyCardDto.setStoryContent(entity.getStoryContent());
				storyCardDto.setLikes(entity.getLikes());
				
				return storyCardDto;
			
			}
		



}


