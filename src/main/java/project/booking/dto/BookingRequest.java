package project.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "CustomerID is required")
    private Long customerID;

    @NotNull(message = "RoomID is required")
    private Long roomID;

    @NotNull(message = "check in date")
    private LocalDate checkInDate;

    @NotNull(message = "chek out date")
    private LocalDate checkOutDate;
}
