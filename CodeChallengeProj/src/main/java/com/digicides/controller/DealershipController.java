package com.digicides.controller;

import java.io.IOException;
import java.util.List;


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
import com.google.maps.NearbySearchRequest.Response;
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
	 */
	@PostMapping(value = "/add")
	public ResponseEntity<Void> addDealer(@RequestBody DealerDto dto) {
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
	@GetMapping("/fetchAll")
	public List<DealerDto> getDealerDetails() {
		return dealerMgmtservice.fetchAllDealerDetails();

	}

	
	/**
	 * Accepting latitude and longitude 
	 * 
	 * @param latitude
	 * @param longitude
	 * @return respective nearest dealership details
	 */
	@GetMapping(path = "/dealer/{latitude}/{longitude}")
	public ResponseEntity<DealerDto> getNearestDealer(@PathVariable double latitude, @PathVariable double longitude) {
		LatLng geocode = null;
		DealerDto dto = null;
		geocode = new LatLng(latitude, longitude);
		dto = dealerMgmtservice.getDealer(geocode);

		return new ResponseEntity<DealerDto>(dto, HttpStatus.OK);
	}

	/**
	 * return as nearest dealer details w.r.t. postcode
	 * 
	 * @param postCode
	 * @return
	 * 
	 */
	@GetMapping("/getDealerByPin/{postCode}")
	public ResponseEntity<DealerDto> getNearestDealerByPin(@PathVariable String postCode){
		LatLng geocode = null;
		Double latitude = null;
		Double longitude = null;
		// getting geocode latitude and longitude from postalCode;
		geocode = geocodeService.getGeocodeByPostalCode(postCode);
		latitude = geocode.lat;
		longitude = geocode.lng;

		// invoking the method by passing these geocodes reult returning back to client

		return getNearestDealer(latitude, longitude);

	}
}
