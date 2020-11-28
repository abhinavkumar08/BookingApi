package io.bookingapi.reservation.validator;

import io.bookingapi.reservation.entity.HotelRoom;
import io.bookingapi.reservation.entity.PaymentMode;
import io.bookingapi.reservation.entity.ReservationPayload;
import io.bookingapi.reservation.exceptions.RequestValidationException;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class ReservationValidatorTest {

    @Test
    public void reeservationRequestValid() throws RequestValidationException {

        ReservationPayload payload = getReservationPayload();
        boolean actual = ReservationValidator.isReservationRequestValid(payload);
        Assertions.assertEquals(true, actual);
    }

    @Test
    public void reservationRequestInvalid(){
        Assertions.assertThrows(RequestValidationException.class, () ->{
            ReservationValidator.isReservationRequestValid(getInvalidReservationPayload());
        });
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

    private ReservationPayload getInvalidReservationPayload() {

        int hotelId = 1;
        int userId = 1;
        int roomId = 1;
        Date checkIn = new DateTime().minusDays(1).toDate();
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


}
