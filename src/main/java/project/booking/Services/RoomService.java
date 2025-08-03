package project.booking.Services;

import jakarta.transaction.Transactional;
import project.booking.data.BookingRepository;
import org.springframework.stereotype.Service;
import project.booking.data.Room;
import project.booking.data.RoomRepository;
import project.booking.exception.RoomNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class RoomService {

    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;

    public RoomService (RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByRoomStatus(Room.RoomStatus.AVAILABLE);
    }

    @Transactional
    public Room updateRoomStatus(Long roomId, Room.RoomStatus newStatus) {
        Room room = getRoomById(roomId);
        room.setRoomStatus(newStatus);
        return roomRepository.save(room);
    }

    public Room saveRoom (Room room) {
        return this.roomRepository.save(room);
    }

    public List<Room> checkAllRooms() {
       return this.roomRepository.findAll();
    }

    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {

        return bookingRepository.countActiveBookingsForRoom(roomId, checkIn, checkOut) == 0;
    }
}
