package project.booking.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.booking.Services.RoomService;
import project.booking.data.Room;
import project.booking.data.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomRepository roomRepository;
    private RoomService roomService;

    public RoomController(RoomRepository roomRepository, RoomService roomService) {
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    @GetMapping("/all")
    CollectionModel<EntityModel<Room>> all() {

        List<EntityModel<Room>> rooms = roomRepository.findAll().stream()
                .map(room -> EntityModel.of(room,
                        linkTo(methodOn(RoomController.class).all()).withRel("rooms")
                ))
                .collect(Collectors.toList());

        return CollectionModel.of(rooms,
                linkTo(methodOn(RoomController.class).all()).withSelfRel());
    }

    @GetMapping("/available-rooms")
    public List<Room> getAvailableRoom() {
        return roomService.getAvailableRooms();
    }

    //Создание комнат здесь не прописываем, мы имеем эту функцию в HotelController
    //Но пропишем @PutMapping для обновления статуса

    @PutMapping("/put/{id}/status")
    public ResponseEntity<Room> updateStatus(@PathVariable Long id,
                                             @RequestParam Room.RoomStatus status) {

        Room roomUpdate = roomService.updateRoomStatus(id, status);
        return ResponseEntity.ok(roomUpdate);
    }

    @DeleteMapping("/rooms")
    public void deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
    }


}
