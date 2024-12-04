package pl.edu.wszib.hotelapp.dao.Initialization;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import pl.edu.wszib.hotelapp.Services.implementations.ReservationService;
import pl.edu.wszib.hotelapp.dao.IReservationDAO;
import pl.edu.wszib.hotelapp.dao.IRoomDAO;
import pl.edu.wszib.hotelapp.model.Reservation;
import pl.edu.wszib.hotelapp.model.Room;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInitialization implements CommandLineRunner {

    private final IReservationDAO reservationDAO;
    private final IRoomDAO roomDAO;

    @Override
    public void run(String... args) throws Exception {
        // standardowe pokoje
        roomDAO.save(new Room(null, Room.RoomType.STANDARD.name(), 4, Room.RoomType.STANDARD.getPricePerNight()));
        roomDAO.save(new Room(null, Room.RoomType.STANDARD.name(), 4, Room.RoomType.STANDARD.getPricePerNight()));
        roomDAO.save(new Room(null, Room.RoomType.STANDARD.name(), 4, Room.RoomType.STANDARD.getPricePerNight()));
        roomDAO.save(new Room(null, Room.RoomType.STANDARD.name(), 4, Room.RoomType.STANDARD.getPricePerNight()));
        // apartamenty
        roomDAO.save(new Room(null, Room.RoomType.APARTMENT.name(), 2, Room.RoomType.APARTMENT.getPricePerNight()));
        roomDAO.save(new Room(null, Room.RoomType.APARTMENT.name(), 2, Room.RoomType.APARTMENT.getPricePerNight()));
        roomDAO.save(new Room(null, Room.RoomType.APARTMENT.name(), 2, Room.RoomType.APARTMENT.getPricePerNight()));
        roomDAO.save(new Room(null, Room.RoomType.APARTMENT.name(), 2, Room.RoomType.APARTMENT.getPricePerNight()));
        // studio
        roomDAO.save(new Room(null, Room.RoomType.STUDIO.name(), 5, Room.RoomType.STUDIO.getPricePerNight()));
        roomDAO.save(new Room(null, Room.RoomType.STUDIO.name(), 5, Room.RoomType.STUDIO.getPricePerNight()));

        // rezerwacje
        reservationDAO.save(new Reservation(null, roomDAO.findById(1L).orElseThrow(), "Adam Malysz", LocalDate.now(), LocalDate.now().plusDays(7),true, true, 0.0, 0.0 , 0.0 ));
        reservationDAO.save(new Reservation(null, roomDAO.findById(6L).orElseThrow(), "Mieczyslaw Kot", LocalDate.now(), LocalDate.now().plusDays(14), true, false, 0.0 , 0.0 , 0.0 ));
        reservationDAO.save(new Reservation(null, roomDAO.findById(10L).orElseThrow(), "Jakub Klepka", LocalDate.now(), LocalDate.now().plusDays(28), false, true,  0.0 , 0.0 , 0.0 ));
    }
}

