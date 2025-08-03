package project.booking.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.booking.data.Hotel;
import project.booking.data.HotelRepository;
import project.booking.data.Room;
import project.booking.dto.HotelWithRoomsRequest;
import project.booking.dto.RoomRequest;
import project.booking.exception.HotelNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HotelController {

    private HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    //Вызов через curl (curl -v localhost:8080/hotels)
    @GetMapping("/hotels")
    public CollectionModel<EntityModel<Hotel>> all() {

        List<EntityModel<Hotel>> hotels = hotelRepository.findAll().stream()
                .map(hotel -> EntityModel.of(hotel,
                        linkTo(methodOn(HotelController.class).all()).withRel("hotels")
                ))
                .collect(Collectors.toList());
        return CollectionModel.of(hotels,
                linkTo(methodOn(HotelController.class).all()).withSelfRel());
    }

    //Вызов через curl (curl -v localhost:8080/hotels/{id})
    @GetMapping("/hotels/{id}")
    public EntityModel<Hotel> one(@PathVariable Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));

        return EntityModel.of(hotel,
                linkTo(methodOn(HotelController.class).all()).withRel("hotels"),
                linkTo(methodOn(HotelController.class).one(hotel.getId())).withSelfRel());

    }

    //Пример добавления в PowerShell $jsonBody = @"
    //{
    //    "nameHotel": "Grand Hotel",
    //    "streetHotel": "123 Main Street",
    //    "cityHotel": "New York",
    //    "stars": "5",
    //    "rooms": [
    //        {
    //            "number": "101",
    //            "price": 100.00,
    //            "roomType": "STANDARD"
    //        },
    //        {
    //            "number": "201",
    //            "price": 200.00,
    //            "roomType": "DELUXE"
    //        }
    //    ]
    //}
    //"@
    //    -Invoke-RestMethod -Uri "http://localhost:8080/hotels-with-rooms" `
    //    -Method Post `
    //    -Body $jsonBody `
    //    -ContentType "application/json"
    // <-------------------------------------------->
    //Отель создается сразу с комнатами Room в одном запросе (для удобства)
    @PostMapping("/hotels")
    ResponseEntity<EntityModel<Hotel>> createHotel(@RequestBody HotelWithRoomsRequest request) {

        Hotel hotel = new Hotel(
                request.getNameHotel(),
                request.getStreetHotel(),
                request.getCityHotel(),
                request.getStars()
        );

        if(request.getRooms() != null) {
            for(RoomRequest roomRequest : request.getRooms()) {
                Room room = new Room(
                        roomRequest.getNumber(),
                        roomRequest.getPrice(),
                        roomRequest.getRoomType(),
                        hotel
                );
                hotel.addRooms(room);
            }
        }

        Hotel newHotel = hotelRepository.save(hotel);

        EntityModel<Hotel> entityModel = EntityModel.of(newHotel,
                linkTo(methodOn(HotelController.class).one(newHotel.getId())).withSelfRel(),
                linkTo(methodOn(HotelController.class).all()).withRel("hotels"));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @DeleteMapping("/hotels")
    public void deleteHotel(@PathVariable Long id) {
        hotelRepository.deleteById(id);
    }


}
