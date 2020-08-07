package com.example.customer.requestmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)      // annotation tells Spring to ignore any attributes not listed in the class
public class StoryBlocksImageLink {
	@JsonProperty("EPS")
	private String eps;
	@JsonProperty("JPG")
	private String jpg;
	@JsonProperty("PDF")
	private String pdf;
	@JsonProperty("PNG")
	private String png;

}
