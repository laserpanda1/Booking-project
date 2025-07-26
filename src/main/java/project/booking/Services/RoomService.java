package project.booking.Services;

import org.springframework.stereotype.Service;
import project.booking.data.Room;
import project.booking.data.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService (RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room saveRoom (Room room) {
        return this.roomRepository.save(room);
    }

    public List<Room> checkAllRooms() {
       return this.roomRepository.findAll();
    }

    public List<Room> getStatus() {
        return this.roomRepository.findByFreeStatus("free");
    }

    public Optional<Room> findById(Long id) {
        return this.roomRepository.findById(id);
    }
}
