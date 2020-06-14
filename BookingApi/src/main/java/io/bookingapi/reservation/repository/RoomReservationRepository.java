package io.bookingapi.reservation.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.bookingapi.reservation.entity.RoomReservation;
import io.bookingapi.reservation.entity.RoomReservationId;

public interface RoomReservationRepository extends CrudRepository<RoomReservation, RoomReservationId> {
	
	public List<RoomReservation> findByRoomId(int roomId);
	
	public List<RoomReservation> findByReservationId(int reservationId);

}
