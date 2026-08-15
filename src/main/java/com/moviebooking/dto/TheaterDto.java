package com.moviebooking.dto;

import com.moviebooking.entity.Theater;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TheaterDto {
    private Long id;
    private String name;
    private String location;
    private String city;

    public static TheaterDto from(Theater theater) {
        return TheaterDto.builder()
                .id(theater.getId())
                .name(theater.getName())
                .location(theater.getLocation())
                .city(theater.getCity())
                .build();
    }
}
