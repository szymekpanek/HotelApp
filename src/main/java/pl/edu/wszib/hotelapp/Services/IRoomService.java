package pl.edu.wszib.hotelapp.Services;

import pl.edu.wszib.hotelapp.model.Room;

import java.util.List;

public interface IRoomService {
    List<Room> getAllRooms();
    Room getRoomById(long roomId);


}
