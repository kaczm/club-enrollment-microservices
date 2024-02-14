package org.bp.projectclient;

import java.io.File;
import org.bp.club.EnrollmentInfo;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.bp.parking.ParkingResponse;
import org.bp.project.model.ClubRequest;
import org.bp.project.model.ClubResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ClubProjectController {

	private static final Logger log = LoggerFactory.getLogger(ClubProjectClientApplication.class);
	
	@org.springframework.beans.factory.annotation.Value("${kafka.gateway.address}")
	private String kafkaGatewayAddress;

	@GetMapping("/enroll")
	public String clubProjectPostForm(Model model) {
		log.info("getmapping enroll");
		ClubRequest clubRequest = new ClubRequest();
		model.addAttribute("clubRequest", clubRequest);
		return "clubProjectPost";
	}

	@GetMapping("/enroll/{id}")
	public String clubProjectGetForm(@PathVariable("id") String id, Model model) {
		log.info("getmapping get enrollment " + id);
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://" + kafkaGatewayAddress + "/api/club/enroll/" + id;
		try {
			ResponseEntity<ClubResponse> response = restTemplate.getForEntity(resourceUrl, ClubResponse.class);
			log.info("clubResponse" + " " + response.getBody().getId() + " " + response.getBody().getEnrollmentInfo()
					+ " " + response.getBody().getParkingResponse());
			model.addAttribute("clubResponse", response.getBody());
			return "clubProjectResult";
		} catch (Exception e) {
			return "clubProjectFault";
		}
	}

	@PostMapping("/enroll")
	public String bookRoom(@ModelAttribute ClubRequest clubRequest, Model model) {
		log.info("postmapping enroll");
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://" + kafkaGatewayAddress + "/api/club/enroll";
		ResponseEntity<ClubResponse> response = restTemplate.postForEntity(resourceUrl, clubRequest,
				ClubResponse.class);
		log.info("clubResponse" + " " + response.getBody().getId() + " " + response.getBody().getEnrollmentInfo() + " "
				+ response.getBody().getParkingResponse());
		if (response.getBody().getEnrollmentInfo() == null)
			response.getBody().setEnrollmentInfo(new EnrollmentInfo());
		if (response.getBody().getParkingResponse() == null)
			response.getBody().setParkingResponse(new ParkingResponse());
		model.addAttribute("clubResponse", response.getBody());
		return "clubProjectResult";
	}
}