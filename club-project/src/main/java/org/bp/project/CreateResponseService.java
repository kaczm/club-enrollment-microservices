package org.bp.project;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.bp.project.model.ClubRequest;
import org.bp.project.model.ClubResponse;
import org.springframework.stereotype.Service;

@Service
public class CreateResponseService {

	private HashMap<String, Response> responses;

	@PostConstruct
	void init() {
		responses = new HashMap<>();
	}

	public static class Response {
//		ClubRequest clubRequest;
		ClubResponse enrollmentResponse;
		ClubResponse parkingResponse;

		public boolean isReady() {
//			return clubRequest != null && enrollmentResponse != null && parkingResponse != null;
			return enrollmentResponse != null && parkingResponse != null;

		}
	}

//	public synchronized boolean addClubRequest(String clubId, ClubRequest clubRequest) {
//		Response response = getResponse(clubId);
//		response.clubRequest = clubRequest;
//		return response.isReady();
//	}

	public synchronized boolean addClubResponse(String clubId, ClubResponse clubResponse, String serviceType) {
		Response response = getResponse(clubId);
		if (serviceType.equals("enrollment"))
			response.enrollmentResponse = clubResponse;
		if (serviceType.equals("parking"))
			response.parkingResponse = clubResponse;
		return response.isReady();
	}

	public synchronized Response getResponse(String clubId) {
		Response response = responses.get(clubId);
		if (response == null) {
			response = new Response();
			responses.put(clubId, response);
		}
		return response;
	}
}
