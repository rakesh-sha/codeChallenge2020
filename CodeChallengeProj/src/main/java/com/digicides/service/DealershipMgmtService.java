package com.digicides.service;

import java.util.List;

import com.digicides.pojo.DealerDto;

/**
 * the Service interface
 * 
 * @author Rakesh
 *
 * @param <T>
 *            the object to store
 * @param <M>
 *            the searching criteria
 */
public interface DealershipMgmtService<T,M> {
	
	/**
	 * add an item
	 * 
	 * @param item
	 */
	public T registerDealer(T item);
	
	/**
	 * retrieve all the items
	 * 
	 * @return
	 */
	
	public List<T> fetchAllDealerDetails();
	
	/**
	 * retrieve an item
	 * 
	 * @return
	 */	
	public T getDealer(M searchingCritearea);
	

}
