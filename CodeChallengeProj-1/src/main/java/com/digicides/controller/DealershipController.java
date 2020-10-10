package com.digicides.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.digicides.geocode.GeocodeService;
import com.digicides.pojo.DealerDto;
import com.digicides.service.DealershipMgmtService;
import com.google.maps.model.LatLng;

/**
 * @RestCtroller
 *  
 * to get all request from client
 * 
 * @author rakes
 *
 */
@RestController
public class DealershipController {
	
	private Logger logger=Logger.getLogger(DealershipController.class);

	/**
	 * the DealerMgmtservice 
	 */
	@Autowired
	private DealershipMgmtService<DealerDto, LatLng> dealerMgmtservice;

	/**
	 * the geocode for validation 
	 */
	@Autowired
	@Qualifier("geocodeService")
	private GeocodeService geocodeService;

	
	/**
	 * To add dealer details
	 * 
	 * @param dto request body all details
	 * @throws Exception 
	 */
	@PostMapping(value = "/add")
	public ResponseEntity<Void> addDealer(@Valid @RequestBody DealerDto dto) throws Exception {
		
			LatLng geocode = geocodeService.getGeocode(dto);
			if (null != geocode) {
				dto.setLatitude(geocode.lat);
				dto.setLongitude(geocode.lng);
				
			} 
			dealerMgmtservice.registerDealer(dto);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * @return all the record of dealer
	 */
	@GetMapping("/All")
	public List<DealerDto> getDealerDetails() {
		List<DealerDto> dtoList=null;
		try {
			dtoList=dealerMgmtservice.fetchAllDealerDetails();
			
		} catch (Exception e) {
			logger.error("Exception while invoking service api fetchAllDealerDetails() method "+e.getCause());
			
		}
		
		
		return dtoList;

	}

	
	/**
	 * Accepting latitude and longitude 
	 * 
	 * @param latitude
	 * @param longitude
	 * @return respective nearest dealership details
	 */
	@GetMapping(path = "/dealer")
	public ResponseEntity<DealerDto> getNearestDealer(@PathParam(value = "latitude") double latitude, @PathParam(value = "longitude") double longitude) {
		LatLng geocode = null;
		DealerDto dto = null;
		geocode = new LatLng(latitude, longitude);
		try {
			dto = dealerMgmtservice.getDealer(geocode);
		} catch (Exception e) {
			logger.error("Exception while invoking service layer getDealer method "+e.getCause());
		}
		

		return new ResponseEntity<DealerDto>(dto, HttpStatus.OK);
	}

	/**
	 * return as nearest dealer details w.r.t. postcode
	 * 
	 * @param postCode
	 * @return
	 * 
	 */
	@GetMapping("/dealer/{postCode}")
	public ResponseEntity<DealerDto> getNearestDealerByPin(@PathVariable String postCode){
		LatLng geocode = null;
		Double latitude = null;
		Double longitude = null;
		// getting geocode latitude and longitude from postalCode;
		try {
		geocode = geocodeService.getGeocodeByPostalCode(postCode);
		}catch (Exception e) {
			logger.error(" Exception while invoking geocoding api "+e.getCause());
		}
		latitude = geocode.lat;
		longitude = geocode.lng;

		// invoking the method by passing these geocodes reult returning back to client

		return getNearestDealer(latitude, longitude);

	}
}
