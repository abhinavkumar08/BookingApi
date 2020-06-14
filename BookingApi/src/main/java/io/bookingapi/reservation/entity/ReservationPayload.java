package io.bookingapi.reservation.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReservationPayload {
	
	private int hotelId;
	
	@JsonFormat(pattern="yyyy-MM-dd")
    private Date checkin;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date checkout;
	
	private int userId;
	
	private List<HotelRoom> rooms;
	
	private PaymentMode paymentMode;

	public ReservationPayload() {}
	
	

	public ReservationPayload(int hotelId, Date checkin, Date checkout, int userId, List<HotelRoom> rooms,
			PaymentMode paymentMode) {
		super();
		this.hotelId = hotelId;
		this.checkin = checkin;
		this.checkout = checkout;
		this.userId = userId;
		this.rooms = rooms;
		this.paymentMode = paymentMode;
	}



	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public Date getCheckout() {
		return checkout;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<HotelRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<HotelRoom> rooms) {
		this.rooms = rooms;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	

}
