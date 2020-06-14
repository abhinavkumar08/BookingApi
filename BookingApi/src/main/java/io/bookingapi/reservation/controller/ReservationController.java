package io.bookingapi.reservation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.bookingapi.reservation.entity.ReservationPayload;
import io.bookingapi.reservation.entity.ReservationResponse;
import io.bookingapi.reservation.exceptions.BookingApiException;
import io.bookingapi.reservation.exceptions.RequestValidationException;
import io.bookingapi.reservation.service.ReservationService;
import io.bookingapi.reservation.validator.ReservationValidator;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
	
	private static Logger logger = LoggerFactory.getLogger(ReservationController.class);

	@Autowired
	private ReservationService reservationService;

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	@ApiOperation(value = "Create Hotel Reservation using this API", notes = "User with a valid user id can book a room in the partner hotel using this API.", response = ReservationResponse.class)
	public ReservationResponse createReservation(@RequestBody ReservationPayload reservationRequest) throws RequestValidationException, BookingApiException {
		
		
		ReservationResponse reservationResponse = null;
		try {
			if(ReservationValidator.isReservationRequestValid(reservationRequest)) {
				logger.info("Creating Reservation");
				reservationResponse = reservationService.createReservation(reservationRequest);
			}
		} catch (RequestValidationException e) {
			logger.error("Invalid request, error occurred due to :"+e.getMessage());
			throw e;
		}
		return reservationResponse;
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
	public List<ReservationResponse> getAllReservation(@PathVariable int userId) {

		return reservationService.getAllReservation(userId);

	}

}
