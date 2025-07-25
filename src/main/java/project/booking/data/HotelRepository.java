package project.booking.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByCityHotel(String cityHotel);
}
