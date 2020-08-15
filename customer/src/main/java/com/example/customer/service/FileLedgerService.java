package com.example.customer.service;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.model.FileLedger;
import com.example.customer.model.ResourceStatus;
import com.example.customer.repository.FileLedgerRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileLedgerService {
	@Autowired
	private FileLedgerRepository fileLedgerRepo;
	
	
	
	public ResourceStatus getResourceStatus(String resourceUri) throws ParseException{
		Optional<FileLedger> fileLedger = fileLedgerRepo.findByResourceUri(resourceUri);
		ResourceStatus resourceStatus= ResourceStatus.UNDEFINED;
		if (fileLedger.isPresent() == false) {
			
			resourceStatus = ResourceStatus.NEW_DOWNLOAD;
			
		}
		else if(fileLedger.get().isRunning()) {
			resourceStatus= ResourceStatus.PROCESS_RUNNING;
		}
		else 
		 {
			long epoch = new java.text
					.SimpleDateFormat("MM/dd/yyyyHH:mm:ss")
					.parse("01/01/1970 01:00:00")
					.getTime() / 1000;

			if(fileLedger.get().getGoFileExpireTime()-epoch>=7200) //2 hours
			{
				resourceStatus = ResourceStatus.ACTIVE;
			}
			else resourceStatus = ResourceStatus.LINK_EXPIRED;
			}
		return resourceStatus;
	}


	public Optional<FileLedger> getFileLedgerByResourceUri(String uri) {

		Optional<FileLedger> fileLedger = fileLedgerRepo.findByResourceUri(uri);
		return fileLedger;
	}
	
	public FileLedger SetFileLedger(FileLedger fileLedger) {
		return fileLedgerRepo.save(fileLedger);
	}

}
