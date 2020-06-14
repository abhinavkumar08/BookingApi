package io.bookingapi.reservation.entity;

public class HotelRoom {

	private int id;

	private String roomType;

	private int pricePerNight;

	private boolean smokingRoom;

	private int occupancy;
	
	public HotelRoom() {}

	public HotelRoom(int id) {
		this.id = id;
	}
	public HotelRoom(int id, String roomType, int pricePerNight, boolean smokingRoom, int occupancy) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.pricePerNight = pricePerNight;
		this.smokingRoom = smokingRoom;
		this.occupancy = occupancy;
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

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}


}
