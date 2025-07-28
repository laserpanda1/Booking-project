package project.booking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.booking.data.*;
import project.booking.data.Room.RoomType;

@Component
public class DataInitializer implements CommandLineRunner {

    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private CustomerRepository customerRepository;

    public DataInitializer (HotelRepository hotelRepository,
                            RoomRepository roomRepository,
                            CustomerRepository customerRepository)
    {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        customerRepository.deleteAll();
        hotelRepository.deleteAll();
        roomRepository.deleteAll();

        Hotel grandLux = hotelRepository.save(new Hotel("GrandLux", "Rose-Broke 41", "Miami", "4"));
        Hotel SeaHotel = hotelRepository.save(new Hotel("SeaHotel", "Gopin-Grave 112", "Minsk", "5"));

        roomRepository.save(new Room("405", 4000, RoomType.STANDARD, grandLux));
        roomRepository.save(new Room("100", 8000, RoomType.DELUXE, grandLux));
        roomRepository.save(new Room("3112", 12000, RoomType.DELUXE, SeaHotel));
        roomRepository.save(new Room("1231", 20000, RoomType.PRESIDENTIAL, SeaHotel));

        customerRepository.save(new Customer("Joe", "Bobin"));
        customerRepository.save(new Customer("Franck", "Rico"));


    }
}
