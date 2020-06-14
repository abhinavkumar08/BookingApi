package io.bookingapi.dataload.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.bookingapi.dataload.entity.Hotel;
import io.bookingapi.dataload.entity.Room;
import io.bookingapi.dataload.service.HotelService;
import io.bookingapi.dataload.service.RoomService;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	private static Logger logger = LoggerFactory.getLogger(HotelController.class);
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RoomService roomService;
	
	@RequestMapping(method=RequestMethod.POST, value="/register")
	public Hotel registerHotel(@RequestBody Hotel hotel) {
		
		logger.info("Registering hotel.");
		return hotelService.registerHotel(hotel);
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Hotel> getAllHotels(){
		
		logger.info("Fetching all list of hotels.");
		return hotelService.getAllHotels();
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{hotelId}/register/room")
	public Room registerRoom(@PathVariable int hotelId, @RequestBody Room room) {
		
		logger.info("Registering room to hotel with hotel id :"+hotelId);
		room.setHotel(new Hotel(hotelId));
		return roomService.registerRoom(room);
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{hotelId}/rooms")
	public List<Room> getAllRooms(@PathVariable int hotelId){
		
		logger.info("Fetching all rooms in the hotel with hotel id :"+hotelId);
		return roomService.getAllRooms(hotelId);
		
	}
	


}
