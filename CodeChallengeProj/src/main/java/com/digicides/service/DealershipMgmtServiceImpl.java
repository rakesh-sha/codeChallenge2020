package com.digicides.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.digicides.entity.Address;
import com.digicides.entity.Dealer;
import com.digicides.pojo.DealerDto;
import com.digicides.reposotory.DealerReposotory;
import com.digicides.utils.Util;
import com.google.maps.model.LatLng;

/**
 * implementation class of service interface
 * 
 * @author rakesh
 *
 */
@Service
public class DealershipMgmtServiceImpl implements DealershipMgmtService<DealerDto,LatLng> {
	
	/**
	 * the repository
	 */
	@Autowired
	private DealerReposotory repo;
	
	
	/**
	 * list to hold records
	 * 
	 */
	private List<Dealer> data=new ArrayList<Dealer>();
	
	/**
	 *to add dealer details into db
	 */
	@Override
	public DealerDto registerDealer(DealerDto dto) {
		Dealer dealerEntity=null;
		//creating Dealer object
		dealerEntity=new Dealer();
		//converting Dto to entity
		BeanUtils.copyProperties(dto, dealerEntity,"address");
		dealerEntity.setAddress(new Address());
		BeanUtils.copyProperties(dto.getAddress(), dealerEntity.getAddress());
		
		//invoking method from reposotry to save obj into db
		dealerEntity=repo.save(dealerEntity);
		//returning result to controller
		BeanUtils.copyProperties(dealerEntity,dto);
		
		return dto;
	}

	/**
	 *to fetch all records
	 *@return
	 */
	@Override
	public List<DealerDto> fetchAllDealerDetails() {
		
		//creating arraylist obj for dtoList
		List<DealerDto> dtoList=new ArrayList<DealerDto>();
		data=repo.findAll();
		/*convirting list of entity to list of dto
		by traversing throgh each entity and copieng to dto
		at last adding to the dtolist
*/		data.forEach(entity->{
			DealerDto dto=new DealerDto();
			BeanUtils.copyProperties(entity, dto);
			dto.setAddress(new com.digicides.pojo.Address());
			BeanUtils.copyProperties(entity.getAddress(), dto.getAddress());
			dtoList.add(dto);
			
		});
		return dtoList;
	}

	
	/**
	 * to get dealer details
	 * @return dealerDto
	 */
	@Override
	public DealerDto getDealer(LatLng geocode) {
		DealerDto dto=null;
		//creating object for dto
		dto=new DealerDto();
		Dealer dealer=findNearest(geocode);
		//converting entity to dto
		BeanUtils.copyProperties(dealer, dto,"address");
		com.digicides.pojo.Address addr=new com.digicides.pojo.Address();
		BeanUtils.copyProperties(dealer.getAddress(), addr);
		dto.setAddress(addr);
		return dto;
	}

	/**
	 * helper method to find nearest dealer
	 * 
	 * @param geocode latitude longitude
	 * @return dealer
	 */
	public Dealer findNearest(LatLng geocode) {
		// customer latitude and longitude
		double lat1 = geocode.lat;
		double lon1 = geocode.lng;
		// hold the nearest distance found till now
		double nearestDist = -1;
		// hold the reference to the nearest shop found till now
		Dealer nearestDealer = null;
		data=repo.findAll();
		
		for (Dealer dealer : data) {
			// latitude and longitude of the shop to compare
			double lat2 = dealer.getLatitude();
			double lon2 = dealer.getLongitude();
			// distance to the shop in comparison
			double dist = Util.haversine(lat1, lon1, lat2, lon2);
			// if the shop in comparison is nearer than the previous shop or if
			// it is the first shop
			if (dist < nearestDist || nearestDist == -1) {
				nearestDealer = dealer;
				nearestDist = dist;
			}
		}
		System.out.println(nearestDealer+" "+data);
		return nearestDealer;
	}
}
