package com.moviebooking.controller;

import com.moviebooking.dto.BookingDto;
import com.moviebooking.dto.CreateBookingRequest;
import com.moviebooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@Valid @RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/ref/{bookingRef}")
    public BookingDto getByRef(@PathVariable String bookingRef) {
        return bookingService.getByRef(bookingRef);
    }

    @GetMapping("/user/{userId}")
    public List<BookingDto> getByUser(@PathVariable Long userId) {
        return bookingService.getByUserId(userId);
    }

    @PutMapping("/ref/{bookingRef}/cancel")
    public BookingDto cancel(@PathVariable String bookingRef) {
        return bookingService.cancelBooking(bookingRef);
    }
}
