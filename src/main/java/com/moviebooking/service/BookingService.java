package com.moviebooking.service;

import com.moviebooking.dto.BookingDto;
import com.moviebooking.dto.CreateBookingRequest;
import com.moviebooking.entity.*;
import com.moviebooking.exception.BookingException;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingDto createBooking(CreateBookingRequest request) {
        Show show = showRepository.findByIdWithDetails(request.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + request.getShowId()));

        if (show.getShowTime().isBefore(LocalDateTime.now())) {
            throw new BookingException("Cannot book a show that has already started");
        }

        List<Long> seatIds = request.getSeatIds().stream().distinct().toList();
        if (seatIds.isEmpty()) {
            throw new BookingException("At least one seat must be selected");
        }

        List<Seat> seats = seatRepository.findAllById(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new BookingException("One or more selected seats do not exist");
        }

        Long screenId = show.getScreen().getId();
        boolean allBelongToScreen = seats.stream().allMatch(seat -> seat.getScreen().getId().equals(screenId));
        if (!allBelongToScreen) {
            throw new BookingException("Selected seats do not belong to this show's screen");
        }

        if (seatRepository.countBookedSeatsForShow(show.getId(), seatIds) > 0) {
            throw new BookingException("One or more selected seats are already booked for this show");
        }


        User user = userRepository.findByEmail(request.getEmail())
                .map(existing -> {
                    existing.setName(request.getName());
                    existing.setPhone(request.getPhone());
                    return userRepository.save(existing);
                })
                .orElseGet(() -> userRepository.save(User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .phone(request.getPhone())
                        .build()));

        BigDecimal total = show.getTicketPrice().multiply(BigDecimal.valueOf(seatIds.size()));
        // Premium seats get a small surcharge
        long premiumCount = seats.stream().filter(s -> "PREMIUM".equalsIgnoreCase(s.getSeatType())).count();
        total = total.add(BigDecimal.valueOf(premiumCount * 50L));

        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .totalAmount(total)
                .status("CONFIRMED")
                .bookingRef(generateBookingRef())
                .build();

        for (Seat seat : seats) {
            BookingSeat bookingSeat = BookingSeat.builder()
                    .booking(booking)
                    .seat(seat)
                    .build();
            booking.getBookingSeats().add(bookingSeat);
        }

        Booking saved = bookingRepository.save(booking);
        return bookingRepository.findByIdWithDetails(saved.getId())
                .map(BookingDto::from)
                .orElseThrow(() -> new ResourceNotFoundException("Booking saved but could not be loaded"));
    }

    @Transactional(readOnly = true)
    public BookingDto getByRef(String bookingRef) {
        return bookingRepository.findByBookingRefWithDetails(bookingRef)
                .map(BookingDto::from)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ref: " + bookingRef));
    }

    @Transactional(readOnly = true)
    public List<BookingDto> getByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return bookingRepository.findByUserIdWithDetails(userId).stream().map(BookingDto::from).toList();
    }

    @Transactional
    public BookingDto cancelBooking(String bookingRef) {
        Booking booking = bookingRepository.findByBookingRefWithDetails(bookingRef)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ref: " + bookingRef));

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new BookingException("Booking is already cancelled");
        }

        if (booking.getShow().getShowTime().isBefore(LocalDateTime.now())) {
            throw new BookingException("Cannot cancel a booking for a show that has already started");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
        return BookingDto.from(booking);
    }

    private String generateBookingRef() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
