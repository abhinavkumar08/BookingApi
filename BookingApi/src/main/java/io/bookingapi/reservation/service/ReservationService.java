package io.bookingapi.reservation.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import io.bookingapi.dataload.entity.Hotel;
import io.bookingapi.dataload.entity.UserDetail;
import io.bookingapi.reservation.entity.HotelRoom;
import io.bookingapi.reservation.entity.Payment;
import io.bookingapi.reservation.entity.PaymentMode;
import io.bookingapi.reservation.entity.PaymentStatus;
import io.bookingapi.reservation.entity.Reservation;
import io.bookingapi.reservation.entity.ReservationPayload;
import io.bookingapi.reservation.entity.ReservationResponse;
import io.bookingapi.reservation.entity.ReservationStatus;
import io.bookingapi.reservation.entity.RoomReservation;
import io.bookingapi.reservation.exceptions.BookingApiException;
import io.bookingapi.reservation.repository.PaymentRepository;
import io.bookingapi.reservation.repository.ReservationRepository;
import io.bookingapi.reservation.repository.RoomReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private RoomReservationRepository roomReservationRepository;

	public void setReservationRepository(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public void setPaymentRepository(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	public void setRoomReservationRepository(RoomReservationRepository roomReservationRepository) {
		this.roomReservationRepository = roomReservationRepository;
	}

	private static final int DUMMY_RESERVATION_ID = 1;

	private static Logger logger = LoggerFactory.getLogger(ReservationService.class);

	public synchronized ReservationResponse createReservation(ReservationPayload reservationPayload) throws BookingApiException {

		int hotelId = reservationPayload.getHotelId();
		List<HotelRoom> rooms = reservationPayload.getRooms();
		Date checkinDate = reservationPayload.getCheckin();
		Date checkoutDate = reservationPayload.getCheckout();
		int userId = reservationPayload.getUserId();

		int amount = 0;
		for (HotelRoom room : rooms) {
			amount += room.getPricePerNight();
		}

		Payment payment = new Payment(0, amount, PaymentStatus.PENDING, reservationPayload.getPaymentMode());
		logger.info("Inserting row with status as PENDING in payment table");
		paymentRepository.save(payment);
		Reservation reservation = new Reservation(DUMMY_RESERVATION_ID, checkinDate, checkoutDate, payment,
				new UserDetail(userId), new Hotel(hotelId), ReservationStatus.CONFIRMED.toString());
		boolean isRoomAvailable = true;

		logger.info("Total number of rooms to be booked " + rooms.size());
		for (HotelRoom room : rooms) {
			List<RoomReservation> roomReservations = roomReservationRepository.findByRoomId(room.getId());
			if (roomReservations != null && !roomReservations.isEmpty()) {

				for (RoomReservation roomReservation : roomReservations) {

					int reservationId = roomReservation.getReservationId();
					Reservation res = reservationRepository.findById(reservationId).get();

					long resCheckInDate = res.getCheckIn().getTime();
					long resCheckOutDate = res.getCheckout().getTime();

					long bookingCheckinDate = checkinDate.getTime();
					long bookingCheckoutDate = checkoutDate.getTime();

					isRoomAvailable = isRoomAvailable(resCheckInDate, resCheckOutDate, bookingCheckinDate,
							bookingCheckoutDate);

					if (!isRoomAvailable) {
						paymentRepository.save(payment);
						logger.info("All or few of the rooms are not available, Aborting request.");
						return new ReservationResponse(null, Collections.emptyList(), "Booking Failed");
					}
				}
			}
		}
		ReservationResponse response = null;
		logger.info("All Rooms are available, proceeding with the booking.");
		try {
			Reservation res = reservationRepository.save(reservation);
			int reservationId = res.getId();
			List<RoomReservation> roomReservations = rooms.stream()
					.map(room -> new RoomReservation(room.getId(), reservationId)).collect(Collectors.toList());
			roomReservationRepository.saveAll(roomReservations);
			// updating payment row with status as SUCCESS if the payment is made by CARD
			if (reservationPayload.getPaymentMode().equals(PaymentMode.CARD)) {
				payment.setStatus(PaymentStatus.SUCCESS);
				paymentRepository.save(payment);
			}
			logger.info("Booking successfull");
			response = new ReservationResponse(res, rooms, "Rooms Booked Successfully");
		} catch (DataAccessException ex) {
			payment.setStatus(PaymentStatus.FAILED);
			paymentRepository.save(payment);
			logger.error("Error occurred while trying to make a reservation " + ex.getMessage());
			throw new BookingApiException(ex);
		}

		return response;
	}

	public List<ReservationResponse> getAllReservation(int userId) {

		logger.info("Fetching all reservation with respect to user with user id: " + userId);

		List<ReservationResponse> reservations = new ArrayList<ReservationResponse>();
		reservationRepository.findByUserDetailId(userId).forEach(r -> {

			List<HotelRoom> rooms = roomReservationRepository.findByReservationId(r.getId()).stream()
					.map(roomR -> new HotelRoom(roomR.getRoomId())).collect(Collectors.toList());

			reservations.add(new ReservationResponse(r, rooms, r.getPayment().getStatus().toString()));
		});
		return reservations;
	}

	private boolean isRoomAvailable(long resCheckInDate, long resCheckOutDate, long bookingCheckInDate,
			long bookingCheckoutDate) {

		// case 1 : booking checkin date should be greater than resCheckin and
		// resCheckout date
		if (bookingCheckInDate >= resCheckOutDate && bookingCheckInDate > resCheckInDate)
			return true;
		// case 2 : booking checkout date should be less than both resCheckoutDate and
		// resCheckinDate
		if (bookingCheckoutDate <= resCheckInDate && bookingCheckoutDate < resCheckOutDate)
			return true;

		return false;
	}

}
