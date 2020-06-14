package io.bookingapi.reservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

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

public class ReservationServiceTest {

	ReservationService reservationService = new ReservationService();

	@Mock
	ReservationRepository mockReservationRepository;
	@Mock
	PaymentRepository mockPaymentRepository;
	@Mock
	RoomReservationRepository mockRoomReservationRepository;
	
	private static final int DUMMY_RESERVATION_ID = 1;

	private void init() {

		mockPaymentRepository = Mockito.mock(PaymentRepository.class);
		mockReservationRepository = Mockito.mock(ReservationRepository.class);
		mockRoomReservationRepository = Mockito.mock(RoomReservationRepository.class);

		reservationService.setPaymentRepository(mockPaymentRepository);
		reservationService.setReservationRepository(mockReservationRepository);
		reservationService.setRoomReservationRepository(mockRoomReservationRepository);
	}

	private ReservationPayload getReservationPayload() {

		int hotelId = 1;
		int userId = 1;
		int roomId = 1;
		Date checkIn = new Date();
		Date checkout = new DateTime().plusDays(2).toDate();
		String roomType = "Deluxe";
		int pricePerNight = 100;
		boolean smokingRoom = false;
		int maxOccupancy = 2;

		HotelRoom room = new HotelRoom(roomId, roomType, pricePerNight, smokingRoom, maxOccupancy);
		List<HotelRoom> rooms = new ArrayList<HotelRoom>();
		rooms.add(room);
		ReservationPayload reservationPayload = new ReservationPayload(hotelId, checkIn, checkout, userId, rooms,
				PaymentMode.CARD);

		return reservationPayload;

	}

	private Payment getPayment(ReservationPayload reservationPayload) {

		return new Payment(1, 475, PaymentStatus.PENDING, reservationPayload.getPaymentMode());

	}

	private Reservation getReservation(ReservationPayload reservationPayload, Payment payment) {

		return new Reservation(DUMMY_RESERVATION_ID, reservationPayload.getCheckin(), reservationPayload.getCheckout(), payment,  new UserDetail(1), new Hotel(1), ReservationStatus.CONFIRMED.toString());

	}

	@Test()
	public void testCreateReservation() throws BookingApiException {

		init();
		ReservationPayload reservationPayload = getReservationPayload();
		Payment payment = getPayment(reservationPayload);

		Reservation reservation = getReservation(reservationPayload, payment);

		Date bookedCheckinDate = new DateTime().minusDays(10).toDate();
		Date bookedCheckoutDate = new DateTime().minusDays(5).toDate();
		Reservation reservationFromDB = new Reservation(DUMMY_RESERVATION_ID, bookedCheckinDate, bookedCheckoutDate, payment,  new UserDetail(1), new Hotel(1), ReservationStatus.CONFIRMED.toString());

		List<Reservation> reservations = new ArrayList<Reservation>();
		reservations.add(reservationFromDB);

		List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		RoomReservation roomReservation = new RoomReservation(1, 1);
		roomReservations.add(roomReservation);

		Mockito.when(mockPaymentRepository.save(ArgumentMatchers.any(Payment.class))).thenReturn(payment);
		Mockito.when(mockReservationRepository.findById(1)).thenReturn(Optional.of(reservationFromDB));
		Mockito.when(mockReservationRepository.save(ArgumentMatchers.any(Reservation.class))).thenReturn(reservation);
		Mockito.when(mockRoomReservationRepository.findByRoomId(1)).thenReturn(roomReservations);
		Mockito.when(mockRoomReservationRepository.saveAll(ArgumentMatchers.any(ArrayList.class)))
				.thenReturn(roomReservations);

		List<HotelRoom> rooms = reservationPayload.getRooms();
		ReservationResponse reservationResponseExpected = new ReservationResponse(reservation, rooms,
				"Rooms Booked Successfully");
		assertEquals(reservationResponseExpected.getRooms(),
				reservationService.createReservation(reservationPayload).getRooms());

	}
	
	
	
	@Test
	public void testCreateReservationButRoomIsNotAvailable() throws BookingApiException {

		init();
		ReservationPayload reservationPayload = getReservationPayload();
		Payment payment = getPayment(reservationPayload);

		Reservation reservation = getReservation(reservationPayload, payment);

		List<Reservation> reservations = new ArrayList<Reservation>();
		reservations.add(reservation);

		List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		RoomReservation roomReservation = new RoomReservation(1, 1);
		roomReservations.add(roomReservation);

		Mockito.when(mockPaymentRepository.save(ArgumentMatchers.any(Payment.class))).thenReturn(payment);
		Mockito.when(mockReservationRepository.findById(1)).thenReturn(Optional.of(reservation));
		Mockito.when(mockReservationRepository.save(ArgumentMatchers.any(Reservation.class))).thenReturn(reservation);
		Mockito.when(mockRoomReservationRepository.findByRoomId(1)).thenReturn(roomReservations);
		Mockito.when(mockRoomReservationRepository.saveAll(ArgumentMatchers.any(ArrayList.class)))
				.thenReturn(roomReservations);

		List<HotelRoom> rooms = reservationPayload.getRooms();
		ReservationResponse reservationResponseExpected = new ReservationResponse(null, rooms, "Booking Failed");
		assertEquals(reservationResponseExpected.getMessage(),
				reservationService.createReservation(reservationPayload).getMessage());

	}

	@Test
	public void testGetAllReservation() {

		init();
		ReservationPayload reservationPayload = getReservationPayload();
		Payment payment = getPayment(reservationPayload);
		Date bookedCheckinDate = new DateTime().plusDays(10).toDate();
		Date bookedCheckoutDate = new DateTime().plusDays(15).toDate();
		Reservation reservationFromDB = new Reservation(DUMMY_RESERVATION_ID, bookedCheckinDate, bookedCheckoutDate, payment, new UserDetail(1), new Hotel(1), ReservationStatus.CONFIRMED.toString());

		List<Reservation> reservations = new ArrayList<Reservation>();
		reservations.add(reservationFromDB);

		List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		RoomReservation roomReservation = new RoomReservation(1, 1);
		roomReservations.add(roomReservation);
		Mockito.when(mockReservationRepository.findByUserDetailId(1)).thenReturn(reservations);
		Mockito.when(mockRoomReservationRepository.findByReservationId(1)).thenReturn(roomReservations);
		List<ReservationResponse> reservationResponse = reservationService.getAllReservation(1);
		List<ReservationResponse> expectedReservationResponse = new ArrayList<ReservationResponse>();
		
		HotelRoom room = new HotelRoom(1);
		List<HotelRoom> rooms = new ArrayList<HotelRoom>();
		rooms.add(room);
		ReservationResponse r = new ReservationResponse(reservationFromDB, rooms, "SUCCESS");
		expectedReservationResponse.add(r);
		
		assertEquals(expectedReservationResponse.get(0).getReservation(), reservationResponse.get(0).getReservation());

	}

}
