package project.booking.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameHotel;
    private String streetHotel;
    private String cityHotel;
    private String quantityApartments;
    private String stars;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    public void addRooms(Room room) {
        rooms.add(room);
        room.setHotel(this);
    }

    public void deleteRooms(Room room) {
        rooms.remove(room);
        room.setHotel(null);
    }
}
