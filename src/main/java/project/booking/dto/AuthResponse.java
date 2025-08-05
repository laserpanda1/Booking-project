package project.booking.dto;

import lombok.Builder;

@Builder
public record AuthResponse (
        String token
){}
