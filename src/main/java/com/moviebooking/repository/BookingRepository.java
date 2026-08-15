package com.moviebooking.repository;

import com.moviebooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT DISTINCT b FROM Booking b
            JOIN FETCH b.user
            JOIN FETCH b.show s
            JOIN FETCH s.movie
            JOIN FETCH s.screen sc
            JOIN FETCH sc.theater
            LEFT JOIN FETCH b.bookingSeats bs
            LEFT JOIN FETCH bs.seat
            WHERE b.user.id = :userId
            ORDER BY b.bookingTime DESC
            """)
    List<Booking> findByUserIdWithDetails(@Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT b FROM Booking b
            JOIN FETCH b.user
            JOIN FETCH b.show s
            JOIN FETCH s.movie
            JOIN FETCH s.screen sc
            JOIN FETCH sc.theater
            LEFT JOIN FETCH b.bookingSeats bs
            LEFT JOIN FETCH bs.seat
            WHERE b.bookingRef = :bookingRef
            """)
    Optional<Booking> findByBookingRefWithDetails(@Param("bookingRef") String bookingRef);

    @Query("""
            SELECT DISTINCT b FROM Booking b
            JOIN FETCH b.user
            JOIN FETCH b.show s
            JOIN FETCH s.movie
            JOIN FETCH s.screen sc
            JOIN FETCH sc.theater
            LEFT JOIN FETCH b.bookingSeats bs
            LEFT JOIN FETCH bs.seat
            WHERE b.id = :id
            """)
    Optional<Booking> findByIdWithDetails(@Param("id") Long id);
}
