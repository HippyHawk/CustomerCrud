package com.example.customer.controller;

import java.io.File;
import java.text.ParseException;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.exceptions.InvalidUriException;
import com.example.customer.model.FileLedger;
import com.example.customer.model.ResourceStatus;
import com.example.customer.requestmodel.FileToDownload;
import com.example.customer.requestmodel.GoFileUploadResponse;
import com.example.customer.requestmodel.ShorteResponse;
import com.example.customer.requestmodel.StoryBlocksFileFormat;
import com.example.customer.requestmodel.UriTransfer;
import com.example.customer.service.FileLedgerService;
import com.example.customer.service.GoFileService;
import com.example.customer.service.ShorteLinkEncodingService;
import com.example.customer.service.StoryBlocksApiService;
import com.example.customer.service.StoryBlocksDownloadDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/storyblocks")
public class StoryBlocksApiController {
	@Autowired
	private StoryBlocksApiService storyBlocksApiService;
	@Autowired
	private StoryBlocksDownloadDataService storyBlocksDownloadDataService;
	@Autowired
	private GoFileService goFileService;
	@Autowired
	private ShorteLinkEncodingService shorteLinkEncodingService;
	@Autowired
	private FileLedgerService fileLedgerService;
	
	
	@PostMapping("/filetype")
	public Callable<ResponseEntity<?>> getFileFormatOptions 
	(@RequestBody UriTransfer body) throws RuntimeException, ParseException{

		String uri = body.getUri();

		if  (uri==null || uri.isEmpty()) {
			throw new InvalidUriException("Uri is Empty!");
		}
		
		String result = storyBlocksApiService
						.getResponseFromStoryBlocks(uri);
		
		String id = storyBlocksApiService
					.extractId("\"details\":\\{\"stockItem\":\\{\"id\":", result);
		
		if (id == null || id.isEmpty()) {
			throw new InvalidUriException
			("Could not identifiy {id} of StoryBlocks resource in Uri: {}"+uri);
		}
		String fileFormatDownloadLink = storyBlocksApiService
									  .createDownloadLink(id, uri);
			    
	    String downloadLinks = storyBlocksApiService
	    					 .getDownloadLinks(fileFormatDownloadLink).getBody();
	    
	    ResponseEntity<StoryBlocksFileFormat> response;
		try {
			response = storyBlocksApiService
					 .getFileFormats(downloadLinks, uri);
			
			
		    return()-> new ResponseEntity<StoryBlocksFileFormat>
		    				(response.getBody(),HttpStatus.OK);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return()-> new ResponseEntity<String> 
		("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);	    
//		 return()-> new ResponseEntity<String> (downloadLink,HttpStatus.OK);
//	    return() -> new ResponseEntity<StoryBlocksImageLink> (storyBlocksImageLink,HttpStatus.OK);		
	}
	
	
	
	@PostMapping("/downloadlink")
	public Callable<ResponseEntity<?>> createDownloadLink (@RequestBody FileToDownload body) 
										throws RuntimeException, ParseException, JsonMappingException, JsonProcessingException
	{		 

//		ResourceStatus resStatus = fileLedgerService.getResourceStatus(body.getResourceUri());
//		
//		
//		if(resStatus == ResourceStatus.ACTIVE) {
//			Optional<FileLedger> fileLedger = fileLedgerService
//					.getFileLedgerByResourceUri(body.getResourceUri());
//
//			return()-> new ResponseEntity<FileLedger>(fileLedger.get() ,HttpStatus.OK);
//		}
//		else if (resStatus == ResourceStatus.NEW_DOWNLOAD
//				|| resStatus == ResourceStatus.LINK_EXPIRED)
//		
//	{
			String uri = body.getResourceUri();
//			FileLedger fileLedger = new FileLedger();
//
//			fileLedger.setRunning(true);
//			fileLedger.setResourceUri(uri);
//			fileLedgerService.SetFileLedger(fileLedger);
		//-------------------Repeated from above-----------------
			

			if  (uri==null || uri.isEmpty()) {
				throw new InvalidUriException("Uri is Empty!");
			}
			
			String result = storyBlocksApiService
							.getResponseFromStoryBlocks(uri);
			
			String id = storyBlocksApiService
						.extractId("\"details\":\\{\"stockItem\":\\{\"id\":", result);
			
			if (id == null || id.isEmpty()) {
				throw new InvalidUriException
				("Could not identifiy {id} of StoryBlocks resource in Uri: {}"+uri);
			}
			String fileFormatDownloadLink = storyBlocksApiService
										  .createDownloadLink(id, uri);
				    
		    String downloadLinks = storyBlocksApiService
		    					 .getDownloadLinks(fileFormatDownloadLink).getBody();
		//-------------------------------------------------------
		String fileDownloadLink = storyBlocksApiService.getFileDownloadLink(downloadLinks, body.getType(), body.getFormat(), body.getRes());
//		fileLedger.setResourceUri(fileDownloadLink);
//		fileLedger.setRunning(true);
//		fileLedgerService.SetFileLedger(fileLedger);
		
		
		
		File file = storyBlocksDownloadDataService.downloadFile(fileDownloadLink);
		    
		String server = goFileService.returnServer();
		    
		ResponseEntity<GoFileUploadResponse> goFileUploadResponse = goFileService
				 													.uploadFile(server, file);
		    
		ResponseEntity<ShorteResponse> shorteResponse 
		    		=shorteLinkEncodingService
		    		.shortenLink("https://gofile.io/d/" +goFileUploadResponse
		    					.getBody()
		    					.getData()
		    					.getCode()
		    					); 
		FileLedger fileLedger = new FileLedger();
		 fileLedger.setEncryptedDownloadLink(shorteResponse.getBody().getShortenedUrl()); 
		 fileLedger.setRunning(false);
//		 fileLedgerService.SetFileLedger(fileLedger);

		 
		 return()-> new ResponseEntity<FileLedger> (fileLedger,HttpStatus.OK);
//	}
//		
//		else if (resStatus== ResourceStatus.PROCESS_RUNNING) {
//			Optional<FileLedger> fileLedger = fileLedgerService
//					.getFileLedgerByResourceUri(body.getResourceUri());
//			return()-> new ResponseEntity<FileLedger>(fileLedger.get(), HttpStatus.PROCESSING);
//		}
		
//		return()-> new ResponseEntity<String>(resStatus.toString(),HttpStatus.SERVICE_UNAVAILABLE);

	}
	
}
