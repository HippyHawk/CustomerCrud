package com.example.customer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@NotNull	
	private String resourceUri;
	
	private String encryptedDownloadLink;
	
	@JsonIgnore
	private String goFileDownloadLink;
	
	@JsonIgnore
	private String googleDriveLink;
	
	@JsonIgnore
	boolean isUploadedToDrive= false;
	
	boolean isRunning= false;
	//need a time value to capture till when the download link is active
	@JsonIgnore
	int downloadCount;
	
	Long goFileExpireTime;

}
