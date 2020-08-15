package com.example.customer.requestmodel;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown=true)      // annotation tells Spring to ignore any attributes not listed in the class
public class StoryBlocksFileFormat {
//	@JsonProperty("EPS")
//	private String eps;
//	@JsonProperty("JPG")
//	private String jpg;
//	@JsonProperty("PDF")
//	private String pdf;
//	@JsonProperty("PNG")
//	private String png;
	
//	@JsonProperty("Type_Of_Resource")
	private String typeOfResource;
	
//	@JsonProperty("Resource_Uri")
	private String resourceUri;
	
//	@JsonProperty("File_Formats")
	private Set<String> fileFormats;
	
//	@JsonProperty("Video_File_Formats")
	private Map<String,String> fileFormatsRes;
	
	
	

}
