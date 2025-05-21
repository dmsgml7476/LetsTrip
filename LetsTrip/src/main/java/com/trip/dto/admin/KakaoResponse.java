package com.trip.dto.admin;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoResponse {

	private List<Document> documents;
	
	
	@Getter
	@Setter
	public static class Document {
		private String x;
		private String y;
	}
}
