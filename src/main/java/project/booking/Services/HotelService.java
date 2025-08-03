package project.booking.Services;

import java.lang.Exception;
import org.springframework.stereotype.Service;
import project.booking.data.Hotel;
import project.booking.data.HotelRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    private HotelRepository hotelRepo;

    public HotelService(HotelRepository hotelRepo) {
        this.hotelRepo = hotelRepo;
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepo.save(hotel);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    public Optional<Hotel> findById(Long id) {
       return hotelRepo.findById(id);
    }

    public void deleteHotel(Long id) {
        hotelRepo.deleteById(id);
    }


}
