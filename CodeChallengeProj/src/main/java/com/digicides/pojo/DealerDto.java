package com.digicides.pojo;

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

	private String name;
	 
	private Address address;
	
	private Double longitude;
	
	private Double latitude;
	

}
