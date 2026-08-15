package com.moviebooking.service;

import com.moviebooking.dto.MovieDto;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(MovieDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<MovieDto> getMoviesWithUpcomingShows() {
        return movieRepository.findMoviesWithUpcomingShows().stream().map(MovieDto::from).toList();
    }

    @Transactional(readOnly = true)
    public MovieDto getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(MovieDto::from)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }
}
