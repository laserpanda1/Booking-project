package project.booking.dto;

public record AuthRequest (
        String email,
        String password
) {}