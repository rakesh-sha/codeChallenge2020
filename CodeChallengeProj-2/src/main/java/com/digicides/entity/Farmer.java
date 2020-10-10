package com.digicides.entity;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

/**
 * Entity class for holding farmer details
 * 
 * @author rakesh
 *
 */
@Data
@Entity
@Table(name="t_Farmer") 
public class Farmer {
	@Id
	@Type(type="long")
	private Long fo_Number;
	
	@Type(type="string")
	private String territory;
	
	@Type(type="long")
	private Long farmer_Number;
	
	private String call_Time;
	
	

}
