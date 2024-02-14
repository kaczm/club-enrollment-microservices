package org.bp.projectclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

import org.bp.club.Club;
import org.bp.club.Person;
import org.bp.parking.Car;
import org.bp.project.model.ClubRequest;
import org.bp.project.model.ClubResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ClubProjectClientApplication {

	private static final Logger log = LoggerFactory.getLogger(ClubProjectClientApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ClubProjectClientApplication.class, args);
	}

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}
//
//	@Bean
//	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//		return args -> {
//			Club club = new Club();
//			club.setAddress("Poznan");
//			club.setName("Sportowy klub");
//			
//			Person person = new Person();
//			person.setFirstName("Kuba");
//			person.setLastName("Nowak");
//			
//			Car car = new Car();
//			car.setPlate("PO1234");
//			
//			ClubRequest clubRequest = new ClubRequest();
//			clubRequest.setCar(car);
//			clubRequest.setClub(club);
//			clubRequest.setPerson(person);
//			
//			try {
//				ResponseEntity<ClubResponse> clubResponse = restTemplate.postForEntity("http://localhost:8075/api/club/enroll", clubRequest,
//						ClubResponse.class);
//				log.info(clubResponse.getBody().getId() + " " + clubResponse.getBody().getParkingResponse() + " " + clubResponse.getBody().getEnrollmentInfo());
//			} catch (HttpClientErrorException e) { // catch 4xx response codes
//				log.error(e.getResponseBodyAsString());
//			}
//		};
//	}

}
