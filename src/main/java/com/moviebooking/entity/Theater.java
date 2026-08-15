package com.moviebooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theaters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String location;

    @Column(nullable = false, length = 100)
    private String city;
}
