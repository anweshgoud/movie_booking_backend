package com.moviebooking.controller;

import com.moviebooking.dto.TheaterDto;
import com.moviebooking.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    @GetMapping
    public List<TheaterDto> getTheaters(@RequestParam(required = false) String city) {
        if (city != null && !city.isBlank()) {
            return theaterService.getTheatersByCity(city);
        }
        return theaterService.getAllTheaters();
    }
}
