package com.example.customer.controller;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.model.StoryBlocksApi;
import com.example.customer.requestmodel.GoFileUploadResponse;
import com.example.customer.requestmodel.ShorteResponse;
import com.example.customer.requestmodel.StoryBlocksImageLink;
import com.example.customer.service.GoFileService;
import com.example.customer.service.ShorteLinkEncodingService;
import com.example.customer.service.StoryBlocksApiService;
import com.example.customer.service.StoryBlocksDownloadDataService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/storyblock")
public class StoryBlocksApiController {
	@Autowired
	private StoryBlocksApiService storyBlocksApiService;
	@Autowired
	private StoryBlocksDownloadDataService storyBlocksDownloadDataService;
	@Autowired
	private GoFileService goFileService;
	@Autowired
	private ShorteLinkEncodingService shorteLinkEncodingService;
	
	@PostMapping("/downloadlink")
	public Callable<ResponseEntity<?>> createDownloadLink (@RequestBody StoryBlocksApi body) throws IOException{

		String uri = body.getStoryBlocksApi();
		
		String result = storyBlocksApiService.getResponseFromStoryBlocks(uri);
		
		String id = storyBlocksApiService.extractId("\"details\":\\{\"stockItem\":\\{\"id\":", result);
		
		String downloadLink = storyBlocksApiService.createDownloadLink(id, uri);
		
	    StoryBlocksImageLink storyBlocksImageLink = storyBlocksApiService.getImageDownloadLinks(downloadLink);
	    
	    File file = storyBlocksDownloadDataService.downloadFile(storyBlocksImageLink.getPng());
	    
	    String server = goFileService.returnServer();
	    
	    ResponseEntity<GoFileUploadResponse> goFileUploadResponse 
	    = goFileService.uploadFile(server, file);
	    
	    ResponseEntity<ShorteResponse> shorteResponse 
	    		=shorteLinkEncodingService
	    		.shortenLink("https://gofile.io/d/" +goFileUploadResponse.getBody().getData().getCode()); 
	   
	    return()-> new ResponseEntity<String> (shorteResponse.getBody().getShortenedUrl(),HttpStatus.OK);
//		 return()-> new ResponseEntity<String> (downloadLink,HttpStatus.OK);
//	    return() -> new ResponseEntity<StoryBlocksImageLink> (storyBlocksImageLink,HttpStatus.OK);
	    
		
	}
	
	@PostMapping("encodeLink/dummy")
	public ResponseEntity<?> encodeLink(@RequestBody StoryBlocksApi body){
		ResponseEntity<ShorteResponse> shorteResponse 
		=shorteLinkEncodingService
		.shortenLink(body.getContentDownloadApi()); 
		return shorteResponse;
	}

}
