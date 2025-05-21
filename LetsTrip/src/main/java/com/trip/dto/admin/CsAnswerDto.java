package com.trip.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsAnswerDto {
    private Long customerServiceId;
    private String answerText;
    private String status; // 예: "ANSWERD"
}
