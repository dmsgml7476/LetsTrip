package com.trip.service.Member;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.trip.dto.Member.RecommendStoryDto;
import com.trip.entity.Lets.StoryEntity;
import com.trip.repository.Lets.StoryRepository;
import com.trip.repository.Member.UserHashtagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendStoryService {

    private final StoryRepository storyRepository;
    private final UserHashtagRepository userHashtagRepository;

    public List<RecommendStoryDto> getRecommendedStoriesForGuest() {
        List<StoryEntity> stories = storyRepository.findTop8PublicStoriesByLikes(PageRequest.of(0, 8));
        if (stories == null || stories.isEmpty()) return List.of();

        System.out.println("=== 비회원 추천 스토리 목록 ===");
        for (StoryEntity story : stories) {
            System.out.println("제목: " + story.getStoryTitle() + " / 작성자: " + story.getUser().getNickname());
        }

        return stories.stream()
                .map(RecommendStoryDto::from)
                .collect(Collectors.toList());
    }

    public List<RecommendStoryDto> getRecommendedStoriesForUser(Long userId) {
        List<Long> hashtagIds = userHashtagRepository.findHashtagIdsByUserId(userId);
        System.out.println(">> 사용자 해시태그 ID 목록: " + hashtagIds);

        if (hashtagIds == null || hashtagIds.isEmpty()) {
            return getRecommendedStoriesForGuest();
        }

        List<Long> similarUserIds = userHashtagRepository.findUserIdsWithSameHashtags(hashtagIds, userId);
        System.out.println(">> 유사 해시태그 유저 ID: " + similarUserIds);

        List<StoryEntity> stories = similarUserIds == null || similarUserIds.isEmpty()
                ? List.of()
                : storyRepository.findTop8PublicStoriesByUserIdsOrderByLikes(similarUserIds, PageRequest.of(0, 8));

        System.out.println("=== 로그인 유저 추천 스토리 (유사 유저 기준) ===");
        for (StoryEntity s : stories) {
            System.out.println("제목: " + s.getStoryTitle() + " / 작성자: " + s.getUser().getNickname());
        }

        // 부족할 경우, 추가 보충
        int remaining = 8 - stories.size();
        if (remaining > 0) {
            List<Long> alreadyIds = stories.stream().map(StoryEntity::getStoryId).toList();
            List<StoryEntity> fallback = storyRepository.findTopPublicStoriesExcludingIds(alreadyIds, PageRequest.of(0, remaining));

            System.out.println("=== 보충된 공개 추천 스토리 ===");
            for (StoryEntity f : fallback) {
                System.out.println("제목: " + f.getStoryTitle() + " / 작성자: " + f.getUser().getNickname());
            }

            stories.addAll(fallback);
        }

        return stories.stream()
                .map(RecommendStoryDto::from)
                .collect(Collectors.toList());
    }
}
