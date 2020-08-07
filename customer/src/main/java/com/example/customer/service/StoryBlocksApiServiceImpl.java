package com.example.customer.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.customer.requestmodel.StoryBlocksImageLink;

@Service


public class StoryBlocksApiServiceImpl implements StoryBlocksApiService {

	@Override
	public String getResponseFromStoryBlocks(String uri) {
		RestTemplate restTemplate = new RestTemplate();
	    //String result = restTemplate.getForObject(uri, String.class);//addHeader
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        //Execute the method writing your HttpEntity to the request
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);        
        //System.out.println(response.getBody());
		return result.toString();
	}

	@Override
	public String extractId(String pat, String result) {
		  Pattern pattern = Pattern.compile(pat);
		    Matcher matcher = pattern.matcher(result);
		    matcher.find();
		    String id;
		    Integer startPointOfId = matcher.end();
		    Integer endPointOfId = startPointOfId;
		    while (result.charAt(endPointOfId)=='0'||
		    		result.charAt(endPointOfId)=='1'||
		    		result.charAt(endPointOfId)=='2'||
		    		result.charAt(endPointOfId)=='3'||
		    		result.charAt(endPointOfId)=='4'||
		    		result.charAt(endPointOfId)=='5'||
		    		result.charAt(endPointOfId)=='6'||
		    		result.charAt(endPointOfId)=='7'||
		    		result.charAt(endPointOfId)=='8'||
		    		result.charAt(endPointOfId)=='9') {
		    	endPointOfId++;
		    	
		    }
		 
		    id = result.substring(startPointOfId, endPointOfId);
		return id;
	}

	@Override
	public String createDownloadLink(String id, String uri) {
		Pattern pattern = Pattern.compile("www.storyblocks.com/");
		Matcher matcher = pattern.matcher(uri);
		matcher.find();
		String type;
		Integer startPointOfType = matcher.end();
		Integer endPointOfType = startPointOfType+1;
		while (uri.charAt(endPointOfType)!='/') {
			endPointOfType++;
		}
		 type = uri.substring(startPointOfType, endPointOfType);
		 String downloadLink = "https://qpr75670gd.execute-api.us-east-1.amazonaws.com/production//v2/download/"
				 + type +"/"+ id +"?content_type=templates&keywords=logo&num_results=20&page=1&project_id=29122&user_id=12154018";
		return downloadLink;
	}

	@Override
	public StoryBlocksImageLink getImageDownloadLinks(String uri) {
		RestTemplate restTemplate = new RestTemplate();
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        //Execute the method writing your HttpEntity to the request
        ResponseEntity<StoryBlocksImageLink> storyBlocksImageLink = restTemplate.exchange(uri, HttpMethod.GET, entity, StoryBlocksImageLink.class);        
		return storyBlocksImageLink.getBody();
	}

}
