package com.trip.dto.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.trip.constant.Member.CsOption;
import com.trip.constant.Member.CustomerServiceCategory;
import com.trip.constant.Member.Status;
import com.trip.entity.Member.CustomerServiceAnswerEntity;
import com.trip.entity.Member.CustomerServiceEntity;
import com.trip.entity.Member.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CsMgmtDto {

	private Long customerServiceId;
	private String loginId;
	private CsOption csOption;
	private CustomerServiceCategory category;
	private String questionText;
	private LocalDateTime questionTime;
	private String formattedDate;
	private String formattedTime;
	private String questionDateTimeHtml;
	
	private String answerText;
	private Status status;
	
	public static CsMgmtDto from(CustomerServiceEntity entity) {
		CsMgmtDto dto= new CsMgmtDto();
		dto.customerServiceId = entity.getId();
		dto.csOption = entity.getCsOption();
		dto.category = entity.getCategory();
		dto.questionText = entity.getQuestionText();
		dto.questionTime = entity.getQuestionTime();
		dto.status = entity.getStatus();

		dto.loginId = entity.getUser().getLoginId();
		
	    if (entity.getAnswer() != null) {
	        dto.answerText = entity.getAnswer().getAnswerText();
	    } else {
	        dto.answerText = "";
	    }
		
		dto.questionDateTimeHtml =
			    entity.getQuestionTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) +
			    "<br/>" +
			    entity.getQuestionTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		dto.formattedDate = entity.getQuestionTime().format(dateFormatter);
		dto.formattedTime = entity.getQuestionTime().format(timeFormatter);
		
		return dto;
		
	}
}
