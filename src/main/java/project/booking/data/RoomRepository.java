package project.booking.data;

import org.springframework.data.jpa.repository.JpaRepository;
import project.booking.data.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByRoomStatus(Room.RoomStatus status);

}
