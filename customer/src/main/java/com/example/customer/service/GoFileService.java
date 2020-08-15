package com.example.customer.service;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.customer.exceptions.FileServerNotReadyException;
import com.example.customer.requestmodel.GoFileServerJson;
import com.example.customer.requestmodel.GoFileUploadResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GoFileService {
	public String returnServer() throws FileServerNotReadyException{
		RestTemplate restTemplate = new RestTemplate();
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        log.info("Call to get Best server");
        //Execute the method writing your HttpEntity to the request
        ResponseEntity<GoFileServerJson> result = restTemplate.exchange("https://apiv2.gofile.io/getServer", HttpMethod.GET, entity, GoFileServerJson.class);        
        if (result.getBody().getData().getServer().isEmpty() || result.getBody().getData().getServer()==null)
        {
        	throw new FileServerNotReadyException("File Server Not Ready: Please try again after some time");
        }
        	
        
		return result.getBody().getData().getServer();
	}
	
	public ResponseEntity<GoFileUploadResponse> uploadFile(String server, File file){
		
		RestTemplate restTemplate = new RestTemplate();
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("filesUploaded", fileSystemResource);
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);
        
        String url = "https://"+ server + ".gofile.io/upload";
        
        log.info("Uploading file to url:" + url);
        log.info(requestEntity.toString());
        
        ResponseEntity<GoFileUploadResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                GoFileUploadResponse.class);
        log.info(response.getBody().toString());
        return response;
		
	}

}
