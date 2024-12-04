package pl.edu.wszib.hotelapp.Services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wszib.hotelapp.Services.IReservationService;
import pl.edu.wszib.hotelapp.dao.IReservationDAO;
import pl.edu.wszib.hotelapp.dao.IRoomDAO;
import pl.edu.wszib.hotelapp.model.Food;
import pl.edu.wszib.hotelapp.model.Reservation;
import pl.edu.wszib.hotelapp.model.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Service
public class ReservationService implements IReservationService {
    private final IReservationDAO iReservationDAO;
    private final IRoomDAO iRoomDAO;

    @Override
    public Reservation createReservation(long roomId, String guestName, LocalDate checkInDate, LocalDate checkOutDate, boolean hasBreakfast, boolean hasDinner) {
        Room room = iRoomDAO.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        validateDates(checkInDate, checkOutDate, null);

        Reservation newReservation = new Reservation(null, room, guestName, checkInDate, checkOutDate,
                hasBreakfast, hasDinner, 0.0, 0.0, 0.0);

        calculateRoomCost(newReservation);
        calculateMealCost(newReservation);
        calculateStayPrice(newReservation);

        return iReservationDAO.save(newReservation);
    }

    @Override
    public void updateReservation(Long reservationId, String newCheckInDate, String newCheckOutDate, boolean breakFast, boolean dinner) {
        Reservation reservation = iReservationDAO.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        LocalDate newCheckIn = LocalDate.parse(newCheckInDate);
        LocalDate newCheckOut = LocalDate.parse(newCheckOutDate);

        if (newCheckIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("New check-in date cannot be in the past");
        }

        if (newCheckOut.isBefore(newCheckIn)) {
            throw new IllegalArgumentException("New check-out date must be after new check-in date");
        }

        reservation.setCheckInDate(newCheckIn);
        reservation.setCheckOutDate(newCheckOut);
        reservation.setBreakFast(breakFast);
        reservation.setDinner(dinner);

        calculateRoomCost(reservation);
        calculateMealCost(reservation);
        calculateStayPrice(reservation);

        iReservationDAO.save(reservation);
    }



    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate, Reservation reservation) {
        if (checkInDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
        if (checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        if (reservation != null) {
            LocalDate originalCheckIn = reservation.getCheckInDate();
            if (checkInDate.isBefore(originalCheckIn.minusMonths(1))) {
                throw new IllegalArgumentException("New check-in date must not be more than 1 month earlier.");
            }
        }
    }


    @Override
    public Reservation findReservationById(Long reservationId) {
        return iReservationDAO.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }

    @Override
    public void calculateMealCost(Reservation reservation) {
        if (reservation.getCheckInDate() != null && reservation.getCheckOutDate() != null) {
            long stayDuration = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
            double mealCost = 0.0;

            if (stayDuration > 0) {
                if (reservation.isBreakFast()) {
                    mealCost += stayDuration * Food.MealType.BREAKFAST.getPrice();
                }
                if (reservation.isDinner()) {
                    mealCost += stayDuration * Food.MealType.DINNER.getPrice();
                }
            }

            reservation.setMealCost(mealCost);
        } else {
            reservation.setMealCost(0.0);
        }
    }

    @Override
    public void calculateRoomCost(Reservation reservation) {
        if (reservation.getRoom() != null && reservation.getCheckInDate() != null && reservation.getCheckOutDate() != null) {
            long stayDuration = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
            double roomCost = stayDuration > 0 ? stayDuration * reservation.getRoom().getPricePerNight() : 0.0;
            reservation.setRoomCost(roomCost);
        } else {
            reservation.setRoomCost(0.0);
        }
    }

    @Override
    public void calculateStayPrice(Reservation reservation) {
        double stayPrice = reservation.getRoomCost() + reservation.getMealCost();
        reservation.setStayPrice(stayPrice);
    }
}
