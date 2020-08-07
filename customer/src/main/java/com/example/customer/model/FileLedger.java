package com.example.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "file")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileLedger {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String resourceUri;
	
	@Column(name = "upload_dir")
	private String uploadDir;
	
	private String encryptedDownloadLink;
	private String goFileDownloadLink;
	boolean isUploadedToDrive;
	boolean isExpired;
	//need a time value to capture till when the download link is active
	int downloadCount;

}
