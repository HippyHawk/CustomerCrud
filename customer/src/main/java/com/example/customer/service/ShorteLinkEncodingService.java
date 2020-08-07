package com.example.customer.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.customer.requestmodel.ShorteResponse;

@Service
public class ShorteLinkEncodingService {
	public ResponseEntity<ShorteResponse> shortenLink(String uri){
		RestTemplate restTemplate = new RestTemplate();
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");
        headers.set("public-api-token", "abfd56ff9c8d5533c9460483fc84b5f7");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("urlToShorten", uri);
        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);
        //Create a new HttpEntity
        
        //Execute the method writing your HttpEntity to the request
        ResponseEntity<ShorteResponse> result = restTemplate.exchange(
        		"https://api.shorte.st/v1/data/url",
        		HttpMethod.PUT, requestEntity,
        		ShorteResponse.class);        
        //System.out.println(response.getBody());
		return result;
	}

}
