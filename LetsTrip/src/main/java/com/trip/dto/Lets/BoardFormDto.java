package com.trip.dto.Lets;

import org.springframework.web.multipart.MultipartFile;

import com.trip.enumType.LocationInfo;
import com.trip.enumType.OpenArea;
import com.trip.enumType.StoryCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardFormDto {
	@NotBlank(message = "제목을 입력해주세요.")
    private String storyTitle;

    private String storyContent;

    private MultipartFile file; // 첨부 이미지 1개

    private StoryCategory storyCate;

    private OpenArea openArea;

    private LocationInfo locationInfo;

    private String linkUrl; // 입력 링크 (선택 사항)
}
