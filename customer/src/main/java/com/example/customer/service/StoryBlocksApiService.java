package com.example.customer.service;

import org.springframework.http.ResponseEntity;

import com.example.customer.requestmodel.StoryBlocksFileFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface StoryBlocksApiService {
	String getResponseFromStoryBlocks (String uri);
	String extractId(String pattern, String matcher);
	String createDownloadLink(String id, String uri);
	StoryBlocksFileFormat getImageDownloadLinks(String uri);
	ResponseEntity<String> getDownloadLinks(String uri);
	String extractType(String uri);
	ResponseEntity<StoryBlocksFileFormat> getFileFormats(String downloadLinks,String uri) throws JsonMappingException, JsonProcessingException;
	String getFileDownloadLink (String downloadLinks, String type, String format, String res) throws JsonMappingException, JsonProcessingException;
}
