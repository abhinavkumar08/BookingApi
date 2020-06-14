package io.bookingapi.dataload.repository;

import org.springframework.data.repository.CrudRepository;

import io.bookingapi.dataload.entity.Hotel;

public interface HotelRepository extends CrudRepository<Hotel, Integer> {

}
