package io.bookingapi.reservation.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(RoomReservationId.class)
public class RoomReservation {
	
	@Id
	private int roomId;
	@Id
	private int reservationId;
	
	public RoomReservation() {}

	public RoomReservation(int roomId, int reservationId) {
		super();
		this.roomId = roomId;
		this.reservationId = reservationId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}


}
