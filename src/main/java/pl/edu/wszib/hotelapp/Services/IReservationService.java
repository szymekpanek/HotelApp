package pl.edu.wszib.hotelapp.Services;

import org.springframework.stereotype.Service;
import pl.edu.wszib.hotelapp.model.Reservation;
import pl.edu.wszib.hotelapp.model.Room;

import java.time.LocalDate;
public interface IReservationService {
    Reservation createReservation(long roomId, String guestName, LocalDate checkInDate, LocalDate checkOutDate, boolean hasBreakfast, boolean hasDinner);
    void updateReservation(Long reservationId, String newCheckInDate, String newCheckOutDate, boolean hasBreakfast, boolean hasDinner);

    Reservation findReservationById(Long reservationId);

    void calculateMealCost(Reservation reservation);

    void calculateRoomCost(Reservation reservation);
    void calculateStayPrice(Reservation reservation);
}
