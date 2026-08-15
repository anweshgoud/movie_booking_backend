package com.moviebooking.dto;

import com.moviebooking.entity.Movie;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private Integer durationMins;
    private BigDecimal rating;
    private String language;
    private String posterUrl;
    private LocalDate releaseDate;

    public static MovieDto from(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .durationMins(movie.getDurationMins())
                .rating(movie.getRating())
                .language(movie.getLanguage())
                .posterUrl(movie.getPosterUrl())
                .releaseDate(movie.getReleaseDate())
                .build();
    }
}
