package com.example.customer.service;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoryBlocksDownloadDataService {
//	@Autowired
//    private RestTemplate restTemplate;
// 
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

//    @Async
//    public CompletableFuture<?> downloadAndUpload(String uri, MultipartFile file){
//    	String encryptedDownloadLink="null right now";
//    	//Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//    	 return CompletableFuture.completedFuture(encryptedDownloadLink);
//    }
	
	
    public File downloadFile(String uri) {
    	  MultiValueMap<String, String> parameters =
    	            UriComponentsBuilder.fromUriString(uri).build().getQueryParams();
    	  String format = parameters.getFirst("format");
    	 RestTemplate restTemplate = new RestTemplate();
 	    final HttpHeaders headers = new HttpHeaders();
         headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:78.0) Gecko/20100101 Firefox/78.0");

         //Create a new HttpEntity
         final HttpEntity<String> entity = new HttpEntity<String>(headers);

     	File file = restTemplate.execute(uri, HttpMethod.GET, null, clientHttpResponse -> {
     	    File ret = File.createTempFile("download","."+ format,new File("C:/Users/Avi/Desktop"));
     		
     	    log.info("Copying started");
     	    
//     	    StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
     	    FileOutputStream stream = new FileOutputStream(ret);
     	    if (clientHttpResponse.getHeaders().getContentLength()==
     	    		StreamUtils.copy(clientHttpResponse.getBody(),stream )) {
     	    	log.info("Successful!");
     	    }
     	    else {
     	    	log.info("Failed!");
     	    }
     	    log.info("Copying ends");
     	    stream.close();
     	    return ret;
     	},entity);
     	       
     	return file;
    }
}
