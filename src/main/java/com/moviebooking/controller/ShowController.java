package com.moviebooking.controller;

import com.moviebooking.dto.SeatDto;
import com.moviebooking.dto.ShowDto;
import com.moviebooking.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping("/movie/{movieId}")
    public List<ShowDto> getShowsByMovie(@PathVariable Long movieId) {
        return showService.getShowsByMovie(movieId);
    }

    @GetMapping("/{id}")
    public ShowDto getShow(@PathVariable Long id) {
        return showService.getShowById(id);
    }

    @GetMapping("/{id}/seats")
    public List<SeatDto> getSeats(@PathVariable Long id) {
        return showService.getSeatsForShow(id);
    }
}
