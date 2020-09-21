package com.digicides.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Type;

import lombok.Data;

/**
 * entity class for dealer details
 * 
 * @author rakesh
 *
 */
@Entity
@Data
public class Dealer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Type(type = "string")
	@Column(length = 100)
	private String name;

	@ManyToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
	private Address address;

	@Type(type = "double")
	@Column(nullable = false)
	private Double longitude;
	
	@Type(type = "double")
	@Column(nullable = false)
	private Double latitude;

}
