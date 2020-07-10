package com.example.customer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.customer.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	//Customer findByAge(int age);

}
