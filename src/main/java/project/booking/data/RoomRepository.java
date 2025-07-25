package project.booking.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByFreeStatus(String status);

}
