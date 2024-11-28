package pl.edu.wszib.hotelapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.hotelapp.model.Reservation;
import pl.edu.wszib.hotelapp.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IReservationDAO extends JpaRepository<Reservation, Long> {
}
