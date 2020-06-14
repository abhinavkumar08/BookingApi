package io.bookingapi.dataload.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.bookingapi.dataload.entity.Room;

public interface RoomRepository extends CrudRepository<Room, Integer> {
	
	public List<Room> findByHotelId(int hotelId);

}
