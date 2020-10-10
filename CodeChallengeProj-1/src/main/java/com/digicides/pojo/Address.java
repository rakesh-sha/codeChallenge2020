package com.digicides.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * pojo class for dealer
 * @author rakesh
 *
 */
@Data
public class Address {
	
	@NotNull
	//@Size(min = 6,message = "postal code must be 6 digit")
	//@Pattern(regexp = "[0-9]",message = "Invalid PostCode")
	@Min(value = 6,message = "postcode must be 6 digit")
	private Integer postCode;
	
	@Size(min = 2, max = 10,message = "addressLine1 should be greater than 2 character")
	private String addressLine1;
	
	@Size(min = 2,message = "addressLine2 should be greater than 2 character")
	private String addressLine2;

}