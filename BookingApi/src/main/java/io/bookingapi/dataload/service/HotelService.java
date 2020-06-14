package io.bookingapi.dataload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.bookingapi.dataload.entity.Hotel;
import io.bookingapi.dataload.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;

	public Hotel registerHotel(Hotel hotel) {
		
		return hotelRepository.save(hotel);
		
	}

	public List<Hotel> getAllHotels() {
		
		List<Hotel> hotels = new ArrayList<Hotel>();
		hotelRepository.findAll().forEach(hotels::add);
		
		return hotels;
	}

}
