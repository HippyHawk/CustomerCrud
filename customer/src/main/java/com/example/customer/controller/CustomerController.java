package com.example.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j

public class CustomerController {
	@Autowired
	CustomerRepository customerRepo;
	
	@GetMapping("/get")
	public ResponseEntity<?> getAll(){
		log.info("Get all entries method called");
		
		if (customerRepo.count()!=0) {
			List<Customer> customers = new ArrayList<>();
			customerRepo.findAll().forEach(customers::add);
			return new ResponseEntity<List<Customer>> (customers,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String> ("DB is empty!", HttpStatus.OK);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customerToBeCreated){
		log.info("Received body : {}",customerToBeCreated);
		customerToBeCreated= customerRepo.save(customerToBeCreated);
		log.info("Saved body : {}",customerToBeCreated);
		return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		if (customerRepo.findById(id).isPresent()) {
			return new ResponseEntity<Customer> (customerRepo.findById(id).get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String> ("No entry for id :"+ id, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/delete")
	public ResponseEntity<String> deleteAll() {
		customerRepo.deleteAll();
		return new ResponseEntity<String> ("DB cleared", HttpStatus.OK);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
		if (customerRepo.findById(id).isPresent()) {
			customerRepo.deleteById(id);
			return new ResponseEntity<String> ("Successfully Deleted, id :"+id,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not find entry by id :"+id,HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customerToBeUpdated){
		if (customerRepo.findById(id).isPresent()) {
			customerToBeUpdated.setId(id);
			customerRepo.save(customerToBeUpdated);
			return new ResponseEntity<Customer> (customerRepo.findById(id).get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Could not find entry by Id :"+id,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create/list")
	public ResponseEntity<String> createCustomerByList(@RequestBody List<Customer> customers){
		customerRepo.saveAll(customers); //customers must never be null or must never contain null
		return new ResponseEntity<String>("Created!", HttpStatus.OK);
	}
	
	@PatchMapping("/name/{id}")
	public ResponseEntity<?> updateAge(@PathVariable("id") Long id, @RequestBody String name){
		if (customerRepo.findById(id).isPresent()) {
			Customer customer= new Customer();
			customer = customerRepo.findById(id).get();
			customer.setId(id);
			customer.setName(name);
			customerRepo.save(customer);
			return new ResponseEntity<Customer>(customerRepo.findById(id).get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Invalid id : "+ id , HttpStatus.NOT_FOUND);
		}
	}



}
