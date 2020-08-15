package com.example.customer.requestmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileToDownload {
	private String resourceUri;
	
	private String type;
	
	private String format;
	
	private String res;
	

}
