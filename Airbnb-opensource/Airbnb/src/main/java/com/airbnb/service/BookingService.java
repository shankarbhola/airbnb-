package com.airbnb.service;

import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.User;
import com.airbnb.payload.BookingDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookingService {
    public BookingDto createBooking(long propertyId,User user, BookingDto bookingDto);

    List<BookingDto> getBookings(User user);

    void deleteBooking(long id);
}
