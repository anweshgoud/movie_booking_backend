package com.moviebooking.controller;

import com.moviebooking.dto.MovieDto;
import com.moviebooking.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    //To Fetch Movies
    @GetMapping
    public List<MovieDto> getMovies(@RequestParam(defaultValue = "false") boolean upcomingOnly) {
        return upcomingOnly ? movieService.getMoviesWithUpcomingShows() : movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieDto getMovie(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }
}
