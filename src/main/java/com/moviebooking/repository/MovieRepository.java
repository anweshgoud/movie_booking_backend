package com.moviebooking.repository;

import com.moviebooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("""
            SELECT DISTINCT s.movie FROM Show s
            WHERE s.showTime >= CURRENT_TIMESTAMP
            ORDER BY s.movie.title
            """)
    List<Movie> findMoviesWithUpcomingShows();
}

