package com.digicides.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.Data;

/**
 * parent entity class for address
 * 
 * @author rakes
 *
 */

@Data
@Entity
public class Address {
	
	/**
	 * fields
	 */
	@Id
	@Type(type = "int")
	private Integer postCode;
	
	@Type(type = "string")
	@Column(length = 100)
	private String addressLine1;
	
	@Column(length = 50)
	private String addressLine2;

}