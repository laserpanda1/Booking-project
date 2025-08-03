package project.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class HotelWithRoomsRequest {
    private String nameHotel;
    private String streetHotel;
    private String cityHotel;
    private String stars;
    private List<RoomRequest> rooms;
}


