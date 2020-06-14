package io.bookingapi.reservation.entity;

import java.io.Serializable;

public class RoomReservationId implements Serializable {
	
	private int roomId;
	private int reservationId;
	
	public RoomReservationId() {}

	public RoomReservationId(int roomId, int reservationId) {
		super();
		this.roomId = roomId;
		this.reservationId = reservationId;
	}

}
