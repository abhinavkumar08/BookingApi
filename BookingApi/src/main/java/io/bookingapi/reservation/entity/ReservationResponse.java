package io.bookingapi.reservation.entity;

import java.util.List;

public class ReservationResponse {

	private Reservation reservation;

	private List<HotelRoom> rooms;

	private String message;

	public ReservationResponse() {
	}

	public ReservationResponse(Reservation reservation, List<HotelRoom> rooms, String message) {
		super();
		this.reservation = reservation;
		this.rooms = rooms;
		this.message = message;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<HotelRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<HotelRoom> rooms) {
		this.rooms = rooms;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
