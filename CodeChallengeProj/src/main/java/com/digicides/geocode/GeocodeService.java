package com.digicides.geocode;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * the context
	 */
	private GeoApiContext context;

	/**
	 * proxy settings
	 */
	private boolean proxy;
	private String proxyaddress;
	private String proxyport;
	private String proxyuser;
	private String proxypassword;

	/**
	 * the api key
	 */
	private String apikey;

	/**
	 * initialize the Geo API Context with API key and request handler etc
	 */
	private void initializeGeoApiContext() {
		if (proxy) {
			AuthenticatedOkHttpRequestHandler requestHandler = new AuthenticatedOkHttpRequestHandler();
			Authenticator authenticator = new Authenticator(proxyuser, proxypassword);
			requestHandler.setAuthenticator(authenticator);
			InetSocketAddress address = new InetSocketAddress(proxyaddress, Integer.valueOf(proxyport));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
			requestHandler.setProxy(proxy);
			context = new GeoApiContext(requestHandler);
		} else {
			context = new GeoApiContext();
		}
		context.setApiKey(apikey);
	}

	/**
	 * return the geocode of the Dealer
	 * 
	 * @param Dealer
	 * @return
	 */
	public LatLng getGeocode(DealerDto Dealer) {
		initializeGeoApiContext();
		GeocodingResult[] results = null;
		LatLng geocode = null;
		try {
			results = GeocodingApi.geocode(context, getFormattedAddress(Dealer)).await();
			geocode = results[0].geometry.location;
		} catch (Exception e) {
		}
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
		}
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
		return formattedAddress.toString();
	}

	public void setContext(GeoApiContext context) {
		this.context = context;
	}

	public void setProxyaddress(String proxyaddress) {
		this.proxyaddress = proxyaddress;
	}

	public void setProxyport(String proxyport) {
		this.proxyport = proxyport;
	}

	public void setProxyuser(String proxyuser) {
		this.proxyuser = proxyuser;
	}

	public void setProxypassword(String proxypassword) {
		this.proxypassword = proxypassword;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}

}
