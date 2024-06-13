package com.airbnb.controller;

import com.airbnb.entity.Booking;
import com.airbnb.entity.User;
import com.airbnb.payload.BookingDto;
import com.airbnb.service.BookingService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/addBooking")
    public ResponseEntity<?> createBooking(
            @RequestParam long propertyId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody BookingDto bookingDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        BookingDto booking = bookingService.createBooking(propertyId, user, bookingDto);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<?> getBooking(@AuthenticationPrincipal User user){
        List<BookingDto> bookings = bookingService.getBookings(user);
        if (!bookings.isEmpty()){
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Bookings Found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/cancel/{id}")
    private ResponseEntity<?> deleteBooking(@PathVariable long id){
        bookingService.deleteBooking(id);
        return new ResponseEntity<>("Booking Deleted", HttpStatus.OK);
    }


}
