package project.booking.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private double price;

    public enum RoomStatus {
        AVAILABLE,
        OCCUPIED,
        RESERVED
    }

    public enum RoomType {
        STANDARD,
        DELUXE,
        PRESIDENTIAL
    }

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus = RoomStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Room (String number, double price, RoomType roomType, Hotel hotel) {
        this.number = number;
        this.price = price;
        this.roomType = roomType;
        this.hotel = hotel;
    }
}
