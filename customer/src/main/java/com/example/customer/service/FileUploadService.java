package com.example.customer.service;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

//NOTE: this service a model service and not is use
@Service
public class FileUploadService {

    private RestTemplate restTemplate;

    @Autowired
    public FileUploadService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<String> postFile(String url, File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // This nested HttpEntiy is important to create the correct
        // Content-Disposition entry with metadata "name" and "filename"
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(file.getName())
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<File> fileEntity = new HttpEntity<>(file, fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);
            return response;
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String> ("ERROR!",HttpStatus.NOT_IMPLEMENTED);
        
    }
    
    public ResponseEntity<String> getBestServer(String uri){
    	RestTemplate restTemplate = new RestTemplate();
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        //Execute the method writing your HttpEntity to the request
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);        
        //System.out.println(response.getBody());
		return result;
    }
}
