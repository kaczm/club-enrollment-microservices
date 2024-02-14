package org.bp.project.model;

public class ClubRequest {
	private org.bp.club.Club club;
	private org.bp.club.Person person;
	private org.bp.parking.Car car;
	public org.bp.club.Club getClub() {
		return club;
	}
	public void setClub(org.bp.club.Club club) {
		this.club = club;
	}
	public org.bp.club.Person getPerson() {
		return person;
	}
	public void setPerson(org.bp.club.Person person) {
		this.person = person;
	}
	public org.bp.parking.Car getCar() {
		return car;
	}
	public void setCar(org.bp.parking.Car car) {
		this.car = car;
	}
	
	
}
