package project.booking.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.booking.Services.BookingService;
import project.booking.data.Booking;
import project.booking.data.BookingRepository;
import project.booking.dto.BookingRequest;
import project.booking.dto.BookingResponse;
import project.booking.exception.BookingNotFoundException;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private BookingRepository bookingRepository;
    private BookingService bookingService;

    public BookingController(BookingRepository bookingRepository,
                             BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
    }

    @GetMapping("/all")
    CollectionModel<EntityModel<Booking>> all() {

        List<EntityModel<Booking>> bookings = bookingRepository.findAll().stream()
                .map(booking -> EntityModel.of(booking,
                        linkTo(methodOn(BookingController.class).all()).withRel("all")
                ))
                .collect(Collectors.toList());

        return CollectionModel.of(bookings,
                linkTo(methodOn(BookingController.class).all()).withSelfRel());
    }

    @GetMapping("/one/{id}")
    EntityModel<Booking> one(@PathVariable Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new BookingNotFoundException(id));

        return EntityModel.of(booking,
                linkTo(methodOn(BookingController.class).all()).withRel("all"),
                linkTo(methodOn(BookingController.class).one(booking.getId())).withSelfRel());
    }

    @PostMapping("/create")
    ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid BookingRequest request) {

        BookingRequest booking = new BookingRequest(
                request.getCustomerID(),
                request.getRoomID(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        BookingResponse serviceResponse = bookingService.createBooking(booking);

        BookingResponse response = new BookingResponse(
                serviceResponse.getBookingId(),
                serviceResponse.getStatus(),
                serviceResponse.getMessage(),
                serviceResponse.getCheckInDate(),
                serviceResponse.getCheckOutDate()
        );

        return ResponseEntity
                .created(URI.create("/create/" + response.getBookingId()))
                .body(response);
    }


}
