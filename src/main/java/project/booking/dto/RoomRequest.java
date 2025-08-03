package project.booking.dto;

import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;
import project.booking.data.Room.RoomType;

@Data
public class RoomRequest {
    private String number;
    private double price;
    private RoomType roomType;
}
