package com.moviebooking.dto;

import com.moviebooking.entity.Booking;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookingDto {
    private Long id;
    private String bookingRef;
    private String status;
    private LocalDateTime bookingTime;
    private BigDecimal totalAmount;
    private String userName;
    private String userEmail;
    private String userPhone;
    private ShowDto show;
    private List<String> seats;

    public static BookingDto from(Booking booking) {
        List<String> seatLabels = booking.getBookingSeats().stream()
                .map(bs -> bs.getSeat().getRowLabel() + bs.getSeat().getSeatNumber())
                .sorted()
                .toList();

        return BookingDto.builder()
                .id(booking.getId())
                .bookingRef(booking.getBookingRef())
                .status(booking.getStatus())
                .bookingTime(booking.getBookingTime())
                .totalAmount(booking.getTotalAmount())
                .userName(booking.getUser().getName())
                .userEmail(booking.getUser().getEmail())
                .userPhone(booking.getUser().getPhone())
                .show(ShowDto.from(booking.getShow()))
                .seats(seatLabels)
                .build();
    }
}
