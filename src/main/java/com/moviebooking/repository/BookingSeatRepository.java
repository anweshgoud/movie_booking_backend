package com.moviebooking.repository;

import com.moviebooking.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
}
