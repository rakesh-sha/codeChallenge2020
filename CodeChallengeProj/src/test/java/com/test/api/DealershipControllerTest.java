package com.test.api;

import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.digicides.controller.DealershipController;
import com.digicides.pojo.Address;
import com.digicides.pojo.DealerDto;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DealershipController.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
public class ShopsControllerTest {

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/add");
		template = new TestRestTemplate();
	}

	@Test
	public void updateDealer() throws Exception {
		DealerDto dealer = new DealerDto();
		dealer.setName("Grocery Store");
		Address address = new Address();
		address.setAddressLine1("Amphitheatre Parkway");
		address.setAddressLine2("Mountain View, CA");
		address.setPostCode(94043);
		dealer.setAddress(address);

		ResponseEntity response = template.postForEntity(base.toString(), dealer, null);

		Assert.assertEquals("The Post request should  be successful.", HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void getDealer() throws Exception {
		this.base = new URL("http://localhost:" + port + "/dealer/{latitude}/{longitude}");
		Object[] urlVariables = { "37.422364", "-122.084364" };
		ResponseEntity<DealerDto> response = template.getForEntity(base.toString(), DealerDto.class, urlVariables);

		Assert.assertEquals("The Get request should  be successful.", HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull("Should return a Shop with name.", response.getBody().getName());
		Assert.assertNotNull("Should return a Shop with address.",
				response.getBody().getAddress().getAddressLine1());
	}
}
