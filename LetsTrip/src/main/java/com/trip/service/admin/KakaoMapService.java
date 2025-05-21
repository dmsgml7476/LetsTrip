package com.trip.service.admin;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.*;
import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import com.trip.dto.admin.CoordinateDto;
import com.trip.dto.admin.KakaoResponse;

@Service
public class KakaoMapService {

	private final String kakaoApiKey = "9850359245b3189d9a46c6739045f61d";
	
	public CoordinateDto getCoordinatesFromAddress(String address) {
		RestTemplate restTemplate = new RestTemplate();
				
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + kakaoApiKey);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		String encodedAddress = UriUtils.encodeQueryParam(address, StandardCharsets.UTF_8);
		String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodedAddress;
		
		ResponseEntity<KakaoResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, KakaoResponse.class);

        List<KakaoResponse.Document> documents = response.getBody().getDocuments();
        
        System.out.println("📌 최종 주소 요청: " + address);
        System.out.println("📌 Kakao 응답 documents: " + documents);
        
        if (documents != null && !documents.isEmpty()) {
            KakaoResponse.Document doc = documents.get(0);
            return new CoordinateDto(Float.parseFloat(doc.getY()), Float.parseFloat(doc.getX()));
        }

        return null;
	}
	

}
