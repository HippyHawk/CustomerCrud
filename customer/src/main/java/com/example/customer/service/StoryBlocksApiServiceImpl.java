package com.example.customer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.customer.exceptions.InvalidUriException;
import com.example.customer.exceptions.RestTemplateException;
import com.example.customer.requestmodel.StoryBlocksFileFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class StoryBlocksApiServiceImpl implements StoryBlocksApiService {

	@Override
	public String getResponseFromStoryBlocks(String uri) throws RuntimeException {
		RestTemplate restTemplate = new RestTemplate();
	    
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        //Execute the method writing your HttpEntity to the request
        try {
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);        
        return result.toString();
        }
        catch(RestClientException e) {
        	log.info(e.getMessage());
        	throw new InvalidUriException(e.getMessage()+"\nResource Uri: <"+uri+"> is INVALID"); 
        }
		
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
	public StoryBlocksFileFormat getImageDownloadLinks(String uri) throws RuntimeException {
		RestTemplate restTemplate = new RestTemplate();
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        try {
        ResponseEntity<StoryBlocksFileFormat> storyBlocksImageLink = restTemplate.exchange(uri, HttpMethod.GET, entity, StoryBlocksFileFormat.class);        
        
		return storyBlocksImageLink.getBody();
        }
        catch (RestClientException e) {
        	log.info(e.getMessage());
        	throw new RestTemplateException(e.getMessage()+ "\n Cannot fetch format list ");
        }
	}
	
	@Override
	public ResponseEntity<String> getDownloadLinks(String uri) throws RuntimeException{
		RestTemplate restTemplate = new RestTemplate();
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");
 
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        try {
            ResponseEntity<String> storyBlocksImageLink = 
            		restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);        
            
    		return storyBlocksImageLink;
            }
            catch (RestClientException e) {
            	log.info(e.getMessage());
            	throw new RestTemplateException(e.getMessage()+ "\n Cannot fetch file format download list ");
            }
        
	}

	@Override
	public String extractType(String uri) {
		// TODO Auto-generated method stub
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
		 
		return type;
	}

	@Override
	public ResponseEntity<StoryBlocksFileFormat> getFileFormats(String downloadLinks,String uri) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
            // convert JSON string to Map
            
				
			StoryBlocksFileFormat obj= new StoryBlocksFileFormat();
            obj.setResourceUri(uri);
            obj.setTypeOfResource(this.extractType(uri));
            if(this.extractType(uri).toLowerCase().equals("video"))
            {
            	Map<String, Map<String, String>> map2;
				map2 = mapper.readValue(downloadLinks, Map.class);
				
            	Map<String,String> map3 = new HashMap<String,String>();
            	map2.forEach((k,v)->{map3.put(k, v.keySet().toString());});
            	obj.setFileFormatsRes(map3);
                obj.setFileFormats(map2.keySet());

            
            }
            else {
            	Map<String, String> map;
    			
    			map = mapper.readValue(downloadLinks, Map.class);
                obj.setFileFormats(map.keySet());

            }
    	    return new ResponseEntity<StoryBlocksFileFormat> (obj,HttpStatus.OK);
	}

	@Override
	public String getFileDownloadLink(String downloadLinks, String type, String format, String res) throws JsonMappingException, JsonProcessingException {
		String response;
		ObjectMapper mapper = new ObjectMapper();
		 if(type.toLowerCase().equals("video"))
         {
         	Map<String, Map<String, String>> map2;
				map2 = mapper.readValue(downloadLinks, Map.class);
				
         	Map<String,String> map3 = new HashMap<String,String>();
         	map2.forEach((k,v)->{map3.put(k, v.keySet().toString());});
         	response = map2.get(format).get(res);

         }
         else {
         	Map<String, String> map;
 			
 			map = mapper.readValue(downloadLinks, Map.class);
 			response = map.get(format);

         }
		
		return response;
	}
	
	

}
