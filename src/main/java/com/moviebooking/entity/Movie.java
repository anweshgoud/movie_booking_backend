package com.moviebooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 100)
    private String genre;

    @Column(name = "duration_mins", nullable = false)
    private Integer durationMins;

    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(nullable = false, length = 50)
    private String language;

    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
}
