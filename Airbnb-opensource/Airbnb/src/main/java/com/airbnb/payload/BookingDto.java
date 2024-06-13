package com.airbnb.payload;

import com.airbnb.entity.Property;
import com.airbnb.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDto {
    private Long id;

    @NotNull(message = "Guest Name cannot be null")
    private String guestName;

    @NotNull(message = "Booking Date cannot be null")
    private LocalDate bookingDate;

    @NotNull(message = "Check-in Date cannot be null")
    private LocalDate checkInDate;

    @NotNull(message = "Total Nights cannot be null")
    private Integer totalNights;

    @NotNull(message = "Check-in Time cannot be null")
    private LocalTime checkInTime;

    private Double totalPrice;
    private Property property;
    private User user;

}
