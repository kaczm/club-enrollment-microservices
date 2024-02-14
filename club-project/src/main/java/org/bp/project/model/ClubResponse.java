package org.bp.project.model;

import org.bp.parking.ParkingResponse;
import org.bp.club.EnrollmentInfo; 

public class ClubResponse {
	private String id;
	private ParkingResponse parkingResponse;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private EnrollmentInfo enrollmentInfo;
	
	public ParkingResponse getParkingResponse() {
		return parkingResponse;
	}
	public void setParkingResponse(ParkingResponse parkingResponse) {
		this.parkingResponse = parkingResponse;
	}
	public EnrollmentInfo getEnrollmentInfo() {
		return enrollmentInfo;
	}
	public void setEnrollmentInfo(EnrollmentInfo enrollmentInfo) {
		this.enrollmentInfo = enrollmentInfo;
	}
}
