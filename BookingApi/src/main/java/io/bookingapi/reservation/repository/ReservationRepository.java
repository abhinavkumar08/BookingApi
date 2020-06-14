package io.bookingapi.reservation.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.bookingapi.reservation.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
	
	public List<Reservation> findByUserDetailId(int userId);
	
}
