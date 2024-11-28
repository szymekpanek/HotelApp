package pl.edu.wszib.hotelapp.Services;

import pl.edu.wszib.hotelapp.model.Reservation;
import pl.edu.wszib.hotelapp.model.Room;

import java.util.List;

public interface IHotelService {
    List<Reservation> getAllReservations();
    List<Room> getAllRooms();
}
