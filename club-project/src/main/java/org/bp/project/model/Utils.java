package org.bp.project.model;

import java.time.OffsetDateTime;

import org.bp.parking.ParkingRequest;
import org.bp.club.EnrollClubRequest;

public class Utils {

	public static ParkingRequest prepareParkingRequest(ClubRequest clubRequest) {
		ParkingRequest parkingRequest = new ParkingRequest();
		if (clubRequest.getCar() != null)
			parkingRequest.setCar(clubRequest.getCar());
		return parkingRequest;
	}

	public static EnrollClubRequest prepareEnrollClubRequest(ClubRequest clubRequest) {
		EnrollClubRequest enrollClubRequest = new EnrollClubRequest();
		if (clubRequest.getPerson() != null)
			enrollClubRequest.setPerson(clubRequest.getPerson());
		if (clubRequest.getClub() != null)
			enrollClubRequest.setClub(clubRequest.getClub());
		return enrollClubRequest;
	}

	public static org.bp.parking.ParkingResponse createParkingResponse() {
		org.bp.parking.ParkingResponse parkingResponse = new org.bp.parking.ParkingResponse();
		parkingResponse.setTransactionId(123123312);
		parkingResponse.setValid(false);
		return parkingResponse;
	}

	public static ClubResponse prepareClubResponse(String id) {
		ClubResponse clubResponse = new ClubResponse();
		clubResponse.setId(id);
		clubResponse.setEnrollmentInfo(null);
		clubResponse.setParkingResponse(null);
		return clubResponse;
	}
}
