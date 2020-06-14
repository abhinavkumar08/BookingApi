package io.bookingapi.dataload.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String roomType;
	
	private int pricePerNight;
	
	private boolean smokingRoom;
	
	private int maxOccupancy;
	
	@ManyToOne
	@JoinColumn(name = "hotel_id", nullable = false)
	private Hotel hotel;
	
	public Room() {}
	
	public Room(int id) {
		this.id = id;
	}

	public Room(int id, String roomType, int pricePerNight, boolean smokingRoom, int maxOccupancy, Hotel hotel) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.pricePerNight = pricePerNight;
		this.smokingRoom = smokingRoom;
		this.maxOccupancy = maxOccupancy;
		this.hotel = hotel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(int pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public boolean isSmokingRoom() {
		return smokingRoom;
	}

	public void setSmokingRoom(boolean smokingRoom) {
		this.smokingRoom = smokingRoom;
	}

	public int getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
}
