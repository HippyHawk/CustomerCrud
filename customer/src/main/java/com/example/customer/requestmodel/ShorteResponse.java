package com.example.customer.requestmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShorteResponse {
	private String status;
	private String shortenedUrl;

}
