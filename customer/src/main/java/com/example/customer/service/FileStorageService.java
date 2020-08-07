//package com.example.customer.service;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.customer.model.FileLedger;
//import com.example.customer.repository.FileLedgerRepository;
//
//@Service
//public class FileStorageService {
//	 private final Path fileStorageLocation;     
//	     @Autowired
//	     FileLedgerRepository FileLedgerRepo;
//	 
//	 
//	     @Autowired
//	 
//	     public FileStorageService(FileLedger fileLedger) throws Exception {
//	 
//	         this.fileStorageLocation = Paths.get(fileLedger.getUploadDir())
//	 
//	                 .toAbsolutePath().normalize();
//	 
//	 
//	         try {
//	 
//	             Files.createDirectories(this.fileStorageLocation);
//	 
//	         } catch (Exception ex) {
//	 
//	             throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
//	 
//	         }
//	 
//	     }
//	 
//	 
//	     public String storeFile(MultipartFile file) {
//	 
//	         // Normalize file name
//	 
//	         String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
//	 
//	         String fileName = "";
//	 
//	 
//	 
//	             
//	 
//	          // Copy file to the target location (Replacing existing file with the same name)
//	 
//	             Path targetLocation = this.fileStorageLocation.resolve(fileName);
//	 
//	             Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//	 
//	             
//	 
//	             DocumnentStorageProperties doc = docStorageRepo.checkDocumentByUserId(userId, docType);
//	 
//	             if(doc != null) {
//	 
//	                 doc.setDocumentFormat(file.getContentType());
//	 
//	                 doc.setFileName(fileName);
//	 
//	                 docStorageRepo.save(doc);
//	 
//	                 
//	 
//	             } else {
//	 
//	                 DocumnentStorageProperties newDoc = new DocumnentStorageProperties();
//	 
//	                 newDoc.setUserId(userId);
//	 
//	                 newDoc.setDocumentFormat(file.getContentType());
//	 
//	                 newDoc.setFileName(fileName);
//	 
//	                newDoc.setDocumentType(docType);
//	 
//	                 docStorageRepo.save(newDoc);
//	 
//	             }
//	 
//	 
//	             return fileName;
//	 
//	         
//	 	     }
//	 
//	 
//	    
//
//}
