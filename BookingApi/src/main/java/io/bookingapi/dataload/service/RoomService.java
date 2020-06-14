package io.bookingapi.dataload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.bookingapi.dataload.entity.Room;
import io.bookingapi.dataload.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;

	public Room registerRoom(Room room) {
		
		return roomRepository.save(room);
		
	}

	public List<Room> getAllRooms(int hotelId) {
		
		List<Room> rooms = new ArrayList<Room>();
		roomRepository.findByHotelId(hotelId).forEach(rooms::add);
		return rooms;
	}

}
