package project.booking.Services;

import jakarta.persistence.EntityNotFoundException;
import project.booking.data.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.booking.data.*;
import project.booking.dto.BookingRequest;
import project.booking.dto.BookingResponse;
import project.booking.data.Room.RoomType;
import project.booking.data.Room.RoomStatus;


@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private CustomerRepository customerRepository;
    private RoomRepository roomRepository;
    private RoomService roomService;

    public BookingService(BookingRepository bookingRepository,
                          CustomerRepository customerRepository,
                          RoomRepository roomRepository,
                          RoomService roomService) {
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

        //Построение логики (1 шаг это проверить customer по ID)
        Customer customer = customerRepository.findById(request.getCustomerID())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer not found  with ID : " + request.getCustomerID()));

        //Поиск комнаты и проверка доступности
        Room room = roomRepository.findById(request.getRoomID())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Room not found with ID : " + request.getRoomID()));

        if (room.getRoomStatus() != RoomStatus.AVAILABLE) {
            throw new IllegalArgumentException(
                    "Room ID" + room.getId() + "is not available. Current status : " + room.getRoomStatus());
        }

        //Создание бронирования
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRoom(room);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());

        //Обновление статуса комнаты
        room.setRoomStatus(RoomStatus.RESERVED);
        roomRepository.save(room);

        //Сохранение бронирования
        Booking savedBooking = bookingRepository.save(booking);

        log.info("Saved booking ID : " + savedBooking.getId());

        //Формирование ответа
        return new BookingResponse(
                savedBooking.getId(),
                Booking.BookingStatus.COMPLETED,
                "Booking created for room : " + room.getNumber()
        );

    }
}