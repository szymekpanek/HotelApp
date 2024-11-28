package pl.edu.wszib.hotelapp.Services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wszib.hotelapp.Services.IHotelService;
import pl.edu.wszib.hotelapp.dao.IReservationDAO;
import pl.edu.wszib.hotelapp.dao.IRoomDAO;
import pl.edu.wszib.hotelapp.model.Reservation;
import pl.edu.wszib.hotelapp.model.Room;

import java.util.List;
@AllArgsConstructor

@Service
public class HotelService implements IHotelService {
    IRoomDAO iRoomDAO;
    IReservationDAO iReservationDAO;
    @Override
    public List<Reservation> getAllReservations() {
        return iReservationDAO.findAll();
    }

    @Override
    public List<Room> getAllRooms() {
        return iRoomDAO.findAll();
    }
}
