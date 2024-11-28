package pl.edu.wszib.hotelapp.Services;

import org.springframework.stereotype.Service;
import pl.edu.wszib.hotelapp.model.Reservation;
import pl.edu.wszib.hotelapp.model.Room;

import java.time.LocalDate;
public interface IReservationService {
    Reservation createReservation(long roomId, String guestName, LocalDate checkInDate, LocalDate checkOutDate);
    void updateReservation(Long reservationId, String newCheckInDate, String newCheckOutDate);

    Reservation findReservationById(Long reservationId);

    double calculateStayPrice(Reservation reservation);
}
