package com.moviebooking.repository;

import com.moviebooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByScreenIdOrderByRowLabelAscSeatNumberAsc(Long screenId);

    @Query("""
            SELECT bs.seat.id FROM BookingSeat bs
            JOIN bs.booking b
            WHERE b.show.id = :showId AND b.status = 'CONFIRMED'
            """)
    List<Long> findBookedSeatIdsByShowId(@Param("showId") Long showId);

    @Query(value = """
            SELECT COUNT(*)
            FROM booking_seats bs
            INNER JOIN bookings b ON b.id = bs.booking_id
            WHERE b.show_id = :showId
              AND b.status = 'CONFIRMED'
              AND bs.seat_id IN (:seatIds)
            """, nativeQuery = true)
    long countBookedSeatsForShow(@Param("showId") Long showId, @Param("seatIds") List<Long> seatIds);
}

