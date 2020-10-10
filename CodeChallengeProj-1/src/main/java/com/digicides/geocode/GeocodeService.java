package com.digicides.geocode;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import com.digicides.pojo.Address;
import com.digicides.pojo.DealerDto;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Service to locate the latitude and longitude of a Dealer. Uses Google's
 * Geocoding service.
 * 
 * @author rakesh
 *
 */

public class GeocodeService {
	
	
	/**
	 * getting logger for class
	 */
	private Logger logger=Logger.getLogger(GeocodeService.class);

	/**
	 * the context
	 */
	private GeoApiContext context;

	/**
	 * the api key
	 */
	private String apikey;

	/**
	 * initialize the Geo API Context with API key and request handler etc
	 */
	private void initializeGeoApiContext() {
		
			context = new GeoApiContext();
		context.setApiKey(apikey);
		if(context!=null)
		logger.info("Context has beent initialized for key "+apikey);
		else
			logger.info("context has not initialized");
	}

	/**
	 * return the geocode of the Dealer
	 * 
	 * @param Dealer
	 * @return
	 * @throws Exception 
	 */
	public LatLng getGeocode(DealerDto Dealer) throws Exception {
		initializeGeoApiContext();
		GeocodingResult[] results = null;
		LatLng geocode = null;
		try {
			results = GeocodingApi.geocode(context, getFormattedAddress(Dealer)).await();
			geocode = results[0].geometry.location;
		} catch (Exception e) {
			logger.error("Exception in invoking geocoding api  : "+e.getMessage());
			throw e;
		}
		logger.info("latitude of the address "+getFormattedAddress(Dealer)+" is passed  as"+geocode.toString());
		return geocode;
	}
	
	public LatLng getGeocodeByPostalCode(String postalcode) {
		initializeGeoApiContext();
		GeocodingResult[] results = null;
		LatLng geocode = null;
		try {
			results = GeocodingApi.geocode(context, postalcode).await();
			geocode = results[0].geometry.location;
		} catch (Exception e) {
			logger.error("Exception in invoking geocoding api : "+e.getCause());
		}
		
		logger.info("latitude and longitude of the postalcode "+postalcode+" is founded "+geocode.toString());
		
		return geocode;
	}

	/**
	 * comma separated string formated address
	 * 
	 * @param Dealer
	 * @return
	 */
	private String getFormattedAddress(DealerDto Dealer) {
		Address address = Dealer.getAddress();
		StringBuilder formattedAddress = new StringBuilder();
		if (Objects.nonNull(Dealer.getName())) {
			formattedAddress.append(Dealer.getName()).append(",");
		}
		if (Objects.nonNull(address.getAddressLine1())) {
			formattedAddress.append(address.getAddressLine1()).append(",");
		}
		if (Objects.nonNull(address.getAddressLine2())) {
			formattedAddress.append(address.getAddressLine2()).append(",");
		}
		if (Objects.nonNull(address.getPostCode())) {
			formattedAddress.append(address.getPostCode());
		}
		logger.info("Evaluating geocode for the address :" +formattedAddress.toString());
		return formattedAddress.toString();
	}

	public void setContext(GeoApiContext context) {
		this.context = context;
	}	
	
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

}
