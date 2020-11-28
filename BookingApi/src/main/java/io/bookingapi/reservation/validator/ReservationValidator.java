package io.bookingapi.reservation.validator;

import java.util.Date;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import io.bookingapi.reservation.entity.HotelRoom;
import io.bookingapi.reservation.entity.PaymentMode;
import io.bookingapi.reservation.entity.ReservationPayload;
import io.bookingapi.reservation.exceptions.RequestValidationException;

final public class ReservationValidator {
	
	private ReservationValidator() {}
	
	public static boolean isReservationRequestValid(ReservationPayload reservationPayload) throws RequestValidationException {
		
		
		Date checkinDate = reservationPayload.getCheckin();
		Date checkoutDate = reservationPayload.getCheckout();
		PaymentMode paymentMode = reservationPayload.getPaymentMode();
		
		List<HotelRoom> rooms = reservationPayload.getRooms();
		
		validateCheckinAndCheckoutDates(checkinDate, checkoutDate); 
		validateRooms(rooms);
		validatePayment(paymentMode);
		
		return true;
	}
	
	private static void validateCheckinAndCheckoutDates(Date checkinDate, Date checkoutDate) throws RequestValidationException {
		
		long currTime = new Date().getTime();
		if(checkinDate.getDate()<new Date().getDate()) throw new RequestValidationException("Check In date cannot be of the past.");
		
		int diffDays = Days.daysBetween(new LocalDate(checkinDate), new LocalDate(checkoutDate)).getDays();
		if(diffDays<=0 || diffDays>=30) throw new RequestValidationException("Difference between check in and checkout date can range from 1 to 30.");
		
		int diffFromCurrentDate = Days.daysBetween(new LocalDate(new Date()), new LocalDate(checkinDate)).getDays();
		if(diffFromCurrentDate>60) throw new RequestValidationException("Check in date cannot exceed 60 days from the current date.");
		
	}
	
	
	private static void validateRooms(List<HotelRoom> rooms) throws RequestValidationException {
		
		if(rooms==null || rooms.isEmpty()) throw  new RequestValidationException("Minimum of one room need to be selected");
		
	}
	
	private static void validatePayment(PaymentMode paymentMode) throws RequestValidationException {
		
		for(PaymentMode pm : PaymentMode.values()) {
			if(paymentMode.equals(pm)) return;
		}
		
		throw new RequestValidationException("Payment Mode is not correct, Please select a valid payment mode.");
		
	}

}
