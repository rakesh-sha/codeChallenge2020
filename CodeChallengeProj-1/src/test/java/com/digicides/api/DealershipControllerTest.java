package com.digicides.api;


import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.digicides.CodeChallengeProjApplication;
import com.digicides.pojo.Address;
import com.digicides.pojo.DealerDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CodeChallengeProjApplication.class)
@WebAppConfiguration
class DealershipControllerTest {

	private int port = 8081; 

	private URL base;

	private TestRestTemplate template;

	@BeforeEach
	void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/add");
		template = new TestRestTemplate();
	}

	@Test
	void testAddDealer() {
		DealerDto dealer = new DealerDto();
		dealer.setId(1);
		dealer.setName("Basundhara Agro World");
		Address address = new Address();
		address.setAddressLine1("Sindurpank, Deogarh Road ");
		address.setAddressLine2(", Sambalpur, Odisha");
		address.setPostCode(786005);
		dealer.setAddress(address);

		ResponseEntity response = template.postForEntity(base.toString(), dealer, null);

		Assert.assertEquals("The Post request should  be successful.", HttpStatus.OK, response.getStatusCode());

	}

	@Test
	void testGetNearestDealer() throws MalformedURLException {

		this.base = new URL("http://localhost:" + port + "/dealer/{latitude}/{longitude}");
		Object[] urlVariables = { "21.475035", "83.970297" };
		ResponseEntity<DealerDto> response = template.getForEntity(base.toString(), DealerDto.class, urlVariables);

		Assert.assertEquals("The Get request should  be successful.", HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull("Should return a Shop with name.", response.getBody().getName());
		Assert.assertNotNull("Should return a Shop with address.", response.getBody().getAddress().getAddressLine1());

	}

	@Test
	void testGetNearestDealerByPin() throws MalformedURLException {
		this.base = new URL("http://localhost:" + port + "/getDealerByPin/{postCode}");
		Object[] urlVariables = { "768004" };
		ResponseEntity<DealerDto> response = template.getForEntity(base.toString(), DealerDto.class, urlVariables);

		Assert.assertEquals("The Get request should  be successful.", HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull("Should return a Shop with name.", response.getBody().getName());
		Assert.assertNotNull("Should return a Shop with address.", response.getBody().getAddress().getAddressLine1());

	}

}
