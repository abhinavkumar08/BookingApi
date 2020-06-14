package io.bookingapi.reservation.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.bookingapi.dataload.entity.Hotel;
import io.bookingapi.dataload.entity.UserDetail;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	protected Date checkIn;

	@JsonFormat(pattern = "yyyy-MM-dd")
	protected Date checkout;

	@OneToOne
	protected Payment payment;

	@ManyToOne
	protected UserDetail userDetail;

	@ManyToOne
	protected Hotel hotel;

	private String reservationStatus;

	public Reservation() {

	}

	public Reservation(int id, Date checkIn, Date checkout, Payment payment, UserDetail userDetail, Hotel hotel,
			String reservationStatus) {
		super();
		this.id = id;
		this.checkIn = checkIn;
		this.checkout = checkout;
		this.payment = payment;
		this.userDetail = userDetail;
		this.hotel = hotel;
		this.reservationStatus = reservationStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckout() {
		return checkout;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", checkIn=" + checkIn + ", checkout=" + checkout + ", payment=" + payment
				+ ", userDetail=" + userDetail + ", hotel=" + hotel + ", reservationStatus=" + reservationStatus + "]";
	}

}
