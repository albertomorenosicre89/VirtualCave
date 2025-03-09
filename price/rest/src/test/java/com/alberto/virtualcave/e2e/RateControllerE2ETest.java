package com.alberto.virtualcave.e2e;

import com.alberto.virtualcave.RateApplication;
import com.alberto.virtualcave.dto.Rate;
import com.alberto.virtualcave.entities.RateEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest( classes = RateApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
public class RateControllerE2ETest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void whenGetRate_withId1_return200withRate1()  {
		LocalDate startDate = LocalDate.of(2022, Month.of(1),1);
		LocalDate endDate = LocalDate.of(2022, Month.of(5),31);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<Rate> response = testRestTemplate.exchange("/rates/1", HttpMethod.GET,request , Rate.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		Rate entity = response.getBody();
		assertEquals(1 , entity.getId());
		assertEquals(1 , entity.getProductId());
		assertEquals(1 , entity.getBrandId());
		assertEquals("EUR" , entity.getCurrencyCode());
		assertEquals("1550.0€" , entity.getPrice());
		assertEquals(startDate , entity.getStartDate());
		assertEquals(endDate , entity.getEndDate());
	}

	@Test
	public void whenGetRateWithFilter_withParameter_return200withRate1()  {
		LocalDate startDate = LocalDate.of(2022, Month.of(1),1);
		LocalDate endDate = LocalDate.of(2022, Month.of(5),31);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(
				"{\n" +
						"    \"productId\": 1,\n" +
						"    \"brandId\": 1,\n" +
						"    \"date\": \"2022-01-01\"\n" +
						"}", headers);
		ResponseEntity<Rate> response = testRestTemplate.exchange("/rates", HttpMethod.GET,request , Rate.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		Rate entity = response.getBody();
		assertEquals(1 , entity.getId());
		assertEquals(1 , entity.getProductId());
		assertEquals(1 , entity.getBrandId());
		assertEquals("EUR" , entity.getCurrencyCode());
		assertEquals("1550.0€" , entity.getPrice());
		assertEquals(startDate , entity.getStartDate());
		assertEquals(endDate , entity.getEndDate());
	}

	@Test
	public void whenGetRateWithFilter_withParameterButNothingInDB_returnNothing()  {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(
				"{\n" +
						"    \"productId\": 1,\n" +
						"    \"brandId\": 1,\n" +
						"    \"date\": \"2018-01-01\"\n" +
						"}", headers);
		ResponseEntity<String> response = testRestTemplate.exchange("/rates", HttpMethod.GET,request , String.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
		assertEquals("Rate not found with the parameters selected",response.getBody());
	}

	@Test
	
	public void whenAddRate_withValidParameters_return200()  {
		LocalDate startDate = LocalDate.of(2022, Month.of(1),1);
		LocalDate endDate = LocalDate.of(2022, Month.of(5),31);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(
				"{\n" +
						"    \"productId\": 10,\n" +
						"    \"brandId\": 10,\n" +
						"    \"startDate\": \"2022-01-01\",\n" +
						"    \"endDate\": \"2022-05-31\",\n" +
						"    \"price\": 1550.0,\n" +
						"    \"currencyCode\": \"EUR\"\n" +
						"}", headers);
		ResponseEntity<RateEntity> response = testRestTemplate.exchange("/rates", HttpMethod.POST,request , RateEntity.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		RateEntity result = response.getBody();
		assertEquals(10 , result.getProductId());
		assertEquals(10 , result.getBrandId());
		assertEquals("EUR" , result.getCurrencyCode());
		assertEquals(1550 , result.getPrice());
		assertEquals(startDate , result.getStartDate());
		assertEquals(endDate , result.getEndDate());
	}

	@Test
	
	public void whenUpdateRate_withValidParameters_return200()  {
		LocalDate startDate = LocalDate.of(2022, Month.of(1),1);
		LocalDate endDate = LocalDate.of(2022, Month.of(5),31);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(
				"{\n" +
						"    \"id\": 1,\n" +
						"    \"price\": 150.0\n" +
						"}", headers);
		ResponseEntity<Rate> response = testRestTemplate.exchange("/rates", HttpMethod.PUT,request , Rate.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		Rate result = response.getBody();
		assertEquals(1 , result.getId());
		assertEquals(1 , result.getProductId());
		assertEquals(1 , result.getBrandId());
		assertEquals("EUR" , result.getCurrencyCode());
		assertEquals("150.0" , result.getPrice());
		assertEquals(startDate , result.getStartDate());
		assertEquals(endDate , result.getEndDate());
	}


	@Test
	
	public void whenPostRate_withValidParameters_return200()  {
		LocalDate startDate = LocalDate.of(2022, Month.of(1),1);
		LocalDate endDate = LocalDate.of(2022, Month.of(5),31);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(
				"{\n" +
						"    \"productId\": 20,\n" +
						"    \"brandId\": 20,\n" +
						"    \"startDate\": \"2022-01-01\",\n" +
						"    \"endDate\": \"2022-05-31\",\n" +
						"    \"price\": 150.0,\n" +
						"    \"currencyCode\": \"EUR\"\n" +
						"}", headers);
		ResponseEntity<RateEntity> response = testRestTemplate.exchange("/rates", HttpMethod.POST,request , RateEntity.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		RateEntity result = response.getBody();
		assertEquals(20 , result.getProductId());
		assertEquals(20 , result.getBrandId());
		assertEquals("EUR" , result.getCurrencyCode());
		assertEquals(150 , result.getPrice());
		assertEquals(startDate , result.getStartDate());
		assertEquals(endDate , result.getEndDate());
	}

	@Test
	
	public void whenDeleteRate_withValidParameters_return200()  {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>( headers);
		ResponseEntity<String> response = testRestTemplate.exchange("/rates/6", HttpMethod.DELETE,request , String.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertEquals("Rate delete correctly  with the id selected",response.getBody());
	}

}