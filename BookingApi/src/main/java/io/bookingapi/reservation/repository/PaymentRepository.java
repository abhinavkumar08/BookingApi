package io.bookingapi.reservation.repository;

import org.springframework.data.repository.CrudRepository;

import io.bookingapi.reservation.entity.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
	
	

}
