package com.moviebooking.dto;

import com.moviebooking.entity.Show;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ShowDto {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private Long screenId;
    private String screenName;
    private Long theaterId;
    private String theaterName;
    private String theaterLocation;
    private String city;
    private LocalDateTime showTime;
    private BigDecimal ticketPrice;

    public static ShowDto from(Show show) {
        return ShowDto.builder()
                .id(show.getId())
                .movieId(show.getMovie().getId())
                .movieTitle(show.getMovie().getTitle())
                .screenId(show.getScreen().getId())
                .screenName(show.getScreen().getName())
                .theaterId(show.getScreen().getTheater().getId())
                .theaterName(show.getScreen().getTheater().getName())
                .theaterLocation(show.getScreen().getTheater().getLocation())
                .city(show.getScreen().getTheater().getCity())
                .showTime(show.getShowTime())
                .ticketPrice(show.getTicketPrice())
                .build();
    }
}
