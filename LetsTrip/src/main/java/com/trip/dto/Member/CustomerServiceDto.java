package com.trip.dto.Member;

import com.trip.constant.Member.CsOption;
import com.trip.constant.Member.CustomerServiceCategory;

import lombok.Data;

@Data
public class CustomerServiceDto {

	private CsOption csOption;
	private CustomerServiceCategory category;
	private String questionText;
}
