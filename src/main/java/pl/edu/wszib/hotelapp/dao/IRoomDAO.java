package pl.edu.wszib.hotelapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.hotelapp.model.Room;
public interface IRoomDAO extends JpaRepository<Room, Long> {
}
