package pl.edu.wszib.hotelapp.Services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wszib.hotelapp.Services.IReservationService;
import pl.edu.wszib.hotelapp.dao.IReservationDAO;
import pl.edu.wszib.hotelapp.dao.IRoomDAO;
import pl.edu.wszib.hotelapp.model.Reservation;
import pl.edu.wszib.hotelapp.model.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class ReservationService implements IReservationService {
    IReservationDAO iReservationDAO;
    IRoomDAO iRoomDAO;
    @Override
    public Reservation createReservation(long roomId, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        // 1. Sprawdzanie, czy pokój istnieje
        Room room = iRoomDAO.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // 2. Walidacja daty przyjazdu (nie może być w przeszłości)
        if (checkInDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        // 3. Walidacja daty wyjazdu (musi być późniejsza niż data przyjazdu)
        if (checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        
        // 5. Tworzenie nowej rezerwacji
        Reservation newReservation = new Reservation(null, room, guestName, checkInDate, checkOutDate, 0.0);

        // 6. Obliczanie ceny za pobyt
        double stayPrice = newReservation.getStayPrice();  // Używamy metody obliczającej cenę
        newReservation.setStayPrice(stayPrice);

        // 7. Zapisanie rezerwacji do bazy danych
        return iReservationDAO.save(newReservation);
    }


    @Override
    public void updateReservation(Long reservationId, String newCheckInDate, String newCheckOutDate) {
        Reservation reservation = iReservationDAO.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        LocalDate originalCheckIn = reservation.getCheckInDate();
        LocalDate originalCheckOut = reservation.getCheckOutDate();
        LocalDate newCheckIn = LocalDate.parse(newCheckInDate);
        LocalDate newCheckOut = LocalDate.parse(newCheckOutDate);

        if (newCheckIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("New check-in date cannot be in the past");
        }

        if (newCheckOut.isBefore(newCheckIn)) {
            throw new IllegalArgumentException("New check-out date must be after new check-in date");
        }

        if (newCheckIn.isBefore(originalCheckIn) ||
                ChronoUnit.DAYS.between(originalCheckIn, newCheckIn) > 30) {
            throw new IllegalArgumentException("New check-in date must be within 1 month after the original date");
        }

        reservation.setCheckInDate(newCheckIn);
        reservation.setCheckOutDate(newCheckOut);
        reservation.getStayPrice();
        iReservationDAO.save(reservation);
    }

    @Override
    public Reservation findReservationById(Long reservationId) {
        return iReservationDAO.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }

    @Override
    public double calculateStayPrice(Reservation reservation) {
        if (reservation.getRoom() != null && reservation.getCheckInDate() != null && reservation.getCheckOutDate() != null) {
            long stayDuration = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
            if (stayDuration > 0) {
                return stayDuration * reservation.getRoom().getPricePerNight();
            }
        }
        return 0.0;
    }
}
