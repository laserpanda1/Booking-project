package project.booking.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId (Long custId);
    List<Booking> findByRoomId (Long roomId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE " +
            "b.room.id = :roomID AND " +
            "b.status <> 'CANCELLED' AND " +
            "b.checkOutDate > :checkIn AND " +
            "b.checkInDate < :checkOut")
    int countActiveBookingsForRoom(@Param("roomID")Long id,
                                   @Param("checkIn")LocalDate checkIn,
                                   @Param("checkOut")LocalDate checkOut);
}
