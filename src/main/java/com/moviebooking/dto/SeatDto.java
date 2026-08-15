package com.moviebooking.dto;

import com.moviebooking.entity.Seat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatDto {
    private Long id;
    private String rowLabel;
    private Integer seatNumber;
    private String seatType;
    private boolean booked;
    private String label;

    public static SeatDto from(Seat seat, boolean booked) {
        return SeatDto.builder()
                .id(seat.getId())
                .rowLabel(seat.getRowLabel())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .booked(booked)
                .label(seat.getRowLabel() + seat.getSeatNumber())
                .build();
    }
}
