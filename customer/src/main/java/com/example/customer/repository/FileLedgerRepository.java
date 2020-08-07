package com.example.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.customer.model.FileLedger;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource(collectionResourceRel = "file",path = "file")
public interface FileLedgerRepository extends CrudRepository<FileLedger, Long> {
	FileLedger findByResourceUri(String resourceUri);

}
