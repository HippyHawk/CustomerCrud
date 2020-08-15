package com.example.customer.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message= "User Name cannot be empty. Please provide a User Name.")
	private String userName;
	
	@Email(message= "Please provide a valid Email.")
	@Column(unique=true)
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(min=4, message= "Password must contain atleast 4 characters.")
	private String password;
	
	

}
