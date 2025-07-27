package project.booking.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.booking.data.*;
import project.booking.dto.BookingRequest;
import project.booking.dto.BookingResponse;


@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private CustomerRepository customerRepository;
    private RoomRepository roomRepository;
    private RoomService roomService;

    public BookingService(BookingRepository bookingRepository,
                          CustomerRepository customerRepository,
                          RoomRepository roomRepository,
                          RoomService roomService)
    {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        log.info("Create booking for customer ID : {} and room ID : {} ",
                request.getCustomerID(), request.getRoomID());




}
