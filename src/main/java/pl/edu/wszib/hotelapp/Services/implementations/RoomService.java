package pl.edu.wszib.hotelapp.Services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wszib.hotelapp.Services.IRoomService;
import pl.edu.wszib.hotelapp.model.Room;

import java.util.List;
@AllArgsConstructor
@Service
public class RoomService implements IRoomService {
    @Override
    public List<Room> getAllRooms() {
        return null;
    }

    @Override
    public Room getRoomById(long roomId) {
        return null;
    }
}
