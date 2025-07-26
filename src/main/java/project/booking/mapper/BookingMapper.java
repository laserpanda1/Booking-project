package project.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import project.booking.data.Booking;
import project.booking.dto.BookingResponse;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "customer.id", target = "customerID")
    @Mapping(source = "room.id", target = "roomID")
    @Mapping(source = "room.name", target = "roomName")
    BookingResponse toResponse(Booking booking);

}
