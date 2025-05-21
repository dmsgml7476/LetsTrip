package com.trip.dto.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.trip.constant.Member.CsOption;
import com.trip.constant.Member.CustomerServiceCategory;
import com.trip.constant.Member.Status;
import com.trip.entity.Member.CustomerServiceEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyCsListDto {
	private Long customerServiceId;
    private CsOption csOption;                     // 불편사항, 기타 등
    private CustomerServiceCategory category;      // 스토리, 장소, 기타 등
    private String questionText;
    private LocalDateTime questionTime;
    private String formattedDate;                  // yyyy.MM.dd
    private String formattedTime;                  // HH:mm
    private Status status;                         // WAITING, IN_PROGRESS, ANSWERED
    private String answerText;                     // (답변이 있다면)
    private String answerDate;

    public static MyCsListDto from(CustomerServiceEntity entity) {
        MyCsListDto dto = new MyCsListDto();
        dto.customerServiceId = entity.getId();
        dto.csOption = entity.getCsOption();
        dto.category = entity.getCategory();
        dto.questionText = entity.getQuestionText();
        dto.questionTime = entity.getQuestionTime();
        dto.status = entity.getStatus();
        dto.answerDate = entity.getAnswer() != null ?
                entity.getAnswer().getAnswerTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) :
                null;
        

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        dto.formattedDate = dto.questionTime.format(dateFormatter);
        dto.formattedTime = dto.questionTime.format(timeFormatter);

        dto.answerText = (entity.getAnswer() != null) ? entity.getAnswer().getAnswerText() : "";

        return dto;
    }
	
	
}
