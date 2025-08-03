package project.booking.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.booking.Services.RoomService;
import project.booking.data.Room;
import project.booking.data.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RoomController {

    private RoomRepository roomRepository;
    private RoomService roomService;

    public RoomController(RoomRepository roomRepository, RoomService roomService) {
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    CollectionModel<EntityModel<Room>> all() {

        List<EntityModel<Room>> rooms = roomRepository.findAll().stream()
                .map(room -> EntityModel.of(room,
                        linkTo(methodOn(RoomController.class).all()).withRel("rooms")
                ))
                .collect(Collectors.toList());

        return CollectionModel.of(rooms,
                linkTo(methodOn(RoomController.class).all()).withSelfRel());
    }

    @GetMapping("/rooms/available-rooms")
    public List<Room> getAvailableRoom() {
        return roomService.getAvailableRooms();
    }


}
