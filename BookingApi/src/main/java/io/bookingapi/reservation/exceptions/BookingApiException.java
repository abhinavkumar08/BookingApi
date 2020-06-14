package io.bookingapi.reservation.exceptions;

public class BookingApiException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookingApiException() {
		super();
	}

	public BookingApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BookingApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookingApiException(String message) {
		super(message);
	}

	public BookingApiException(Throwable cause) {
		super(cause);
	}

}
