package com.moviebooking.repository;

import com.moviebooking.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("""
            SELECT s FROM Show s
            JOIN FETCH s.movie
            JOIN FETCH s.screen sc
            JOIN FETCH sc.theater
            WHERE s.movie.id = :movieId AND s.showTime >= CURRENT_TIMESTAMP
            ORDER BY s.showTime
            """)
    List<Show> findUpcomingByMovieId(@Param("movieId") Long movieId);

    @Query("""
            SELECT s FROM Show s
            JOIN FETCH s.movie
            JOIN FETCH s.screen sc
            JOIN FETCH sc.theater
            WHERE s.id = :id
            """)
    Optional<Show> findByIdWithDetails(@Param("id") Long id);
}
