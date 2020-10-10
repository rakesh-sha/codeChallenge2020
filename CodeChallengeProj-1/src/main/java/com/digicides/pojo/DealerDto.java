package com.digicides.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * pojo class for accepting request body
 * 
 * @author rakesh
 *
 */
@Data
public class DealerDto {
	
	private Integer id;

	@NotNull
	@Size(min = 2, message = "Name must be greater than 2 character ")
	private String name;
	 
	private Address address;
	
	private Double longitude;
	
	private Double latitude;
	

}
