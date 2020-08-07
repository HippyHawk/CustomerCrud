package com.example.customer.service;

import com.example.customer.requestmodel.StoryBlocksImageLink;

public interface StoryBlocksApiService {
	String getResponseFromStoryBlocks (String uri);
	String extractId(String pattern, String matcher);
	String createDownloadLink(String id, String uri);
	StoryBlocksImageLink getImageDownloadLinks(String uri);

}
