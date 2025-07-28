package project.booking.Services;

import project.booking.data.BookingRepository;
import org.springframework.stereotype.Service;
import project.booking.data.Room;
import project.booking.data.RoomRepository;

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

    public Room saveRoom (Room room) {
        return this.roomRepository.save(room);
    }

    public List<Room> checkAllRooms() {
       return this.roomRepository.findAll();
    }

    public List<Room> getAvailableStatus() {
        return this.roomRepository.findByRoomStatus(Room.RoomStatus.AVAILABLE);
    }

    public Optional<Room> findById(Long id) {
        return this.roomRepository.findById(id);
    }

    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {

        return bookingRepository.countActiveBookingsForRoom(roomId, checkIn, checkOut) == 0;
    }
}
