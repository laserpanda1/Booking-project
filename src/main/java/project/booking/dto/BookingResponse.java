package project.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.booking.data.Booking;
import project.booking.data.Booking.BookingStatus;

import java.time.LocalDate;

@Data
public class BookingResponse {
    private Long id;
    private Long customerID;
    private Long roomID;
    private Long bookingId;
    private String roomName;
    private LocalDate createdAt;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus status;
    private String confirmationNumber;
    private String message;

    public BookingResponse(Long bookingId, BookingStatus status, String message, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = bookingId;
        this.status = status;
        this.message = message;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

}
