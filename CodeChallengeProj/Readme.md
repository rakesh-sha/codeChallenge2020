# Dealership Near You API
Gives you the facility to add a dealer with address. Stores the longitude and latitude of your dealer using Google [Geocoding API](https://developers.google.com/maps/documentation/geocoding/intro). You can request for a dealer mentioning your current latitude and longitude. The API would return you the dealership nearest to you.

To add a resource
```
Resource			: /add
Description			: Adds a Dealer. The API would find out the latitude and longitude of the 
					  Dealer address using Google Maps API and store it with the address.
Method				: POST
Request Content-type: application/json
Example Request		:	{
    "id": 35246661,
    "name": "Soham AgriMart",
    "address": {
    	 "postCode": 768003 ,
      "addressLine1": "Quntum complex sambalpur odisha" ,
      "addressLine2": "India"
    },
    "latitude": 21.475587,
    "longitude": 83.965254
  }
Success Response	: 200 OK
```

To get a nearest Dealer by latitude and longitude
```
Resource				: /dealer/{latitude}/{longitude}
Description				: Gives you the nearest Dealer from your latitude and longitude.
Method					: GET
Response Content-type	: application/json;charset=UTF-8
Example Response		: 200 OK	
							{
    "id": 35246661,
    "name": "Venkateswar Agro & Tractors",
    "address": {
    	 "postCode": 768005 ,
      "addressLine1": "Padar Para, Themra, Odisha " ,
      "addressLine2": "India"
    },
    "latitude": 21.468502,
    "longitude":84.044915
  }
```
To get a nearest Dealer by PostCode
```
Resource				: getDealerByPin/{postCode}
Description				: Gives you the nearest Dealer from your latitude and longitude.
Method					: GET
Response Content-type	: application/json;charset=UTF-8
Example Response		: 200 OK	
							{
    "id": 35246661,
    "name": "Soham AgriMart",
    "address": {
    	 "postCode": 768003 ,
      "addressLine1": "Quntum complex sambalpur odisha" ,
      "addressLine2": "India"
    },
    "latitude": 21.475587,
    "longitude": 83.965254
  }
```

To get resources list
```
Resource				: /fetchAll
Description				: Gives you the list of available Dealers
Method					: GET
Response Content-type	: application/json;charset=UTF-8
Example Response		: 200 OK	
							[
   {
    "id": 35246661,
    "name": "Soham AgriMart",
    "address": {
    	 "postCode": 768003 ,
      "addressLine1": "Quntum complex sambalpur odisha" ,
      "addressLine2": "India"
    },
    "latitude": 21.475587,
    "longitude": 83.965254
  },
  {
    "id": 35246661,
    "name": "Venkateswar Agro & Tractors",
    "address": {
    	 "postCode": 768005 ,
      "addressLine1": "Padar Para, Themra, Odisha " ,
      "addressLine2": "India"
    },
    "latitude": 21.468502,
    "longitude":84.044915
  }
]  
```


## How to run
You need Java 8 or abouve for running this project.

springboot port=8081
if want to change port u can by going to application.properties file

## If you are behind a proxy server
If you are running it behind a proxy server, set the proxy setting in `application.properties`. e.g.
```
config.proxy=true
config.proxyaddress=<your proxy host address>
config.proxyport=<your proxy port>
config.proxyuser=<user name>
config.proxypassword=<proxy password>
```

Else update the file as `config.proxy=false`.

## Google map's Geocoding API
The Google Maps [Geocoding API](https://developers.google.com/maps/documentation/geocoding/start) is a service that provides you the latitude and longitude of an address which is called  geocoding. It also supports reverse geocoding i.e. gives you the address from the latitude and longitude provided. To use this Geocoding API you need to first [register](https://developers.google.com/maps/documentation/geocoding/get-api-key) your application with Google using your Google ID. After registration Google gives you an API key which you need to update in the `application.properties` file as
```
config.apikey=<your key goes here>

## The distance comparison logic
The [Great-circle distance](https://en.wikipedia.org/wiki/Great-circle_distance) or orthodromic distance is the shortest distance between two points on the surface of a sphere, measured along the surface of the sphere (as opposed to a straight line through the sphere's interior). [Haversine formula](https://en.wikipedia.org/wiki/Haversine_formula) is used to calculate the Great-circle distance between two points on a sphere from their longitudes and latitudes. Refer this [article](http://www.movable-type.co.uk/scripts/latlong.html) for the formula.