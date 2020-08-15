 package com.example.customer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.customer.model.FileLedger;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource(collectionResourceRel = "file",path = "file")
public interface FileLedgerRepository extends CrudRepository<FileLedger, Long> {
	Optional<FileLedger> findByResourceUri(String resourceUri);
	boolean existsByResourceUri(String resourceUri);

}
