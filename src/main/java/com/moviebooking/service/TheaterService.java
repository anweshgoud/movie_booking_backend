package com.moviebooking.service;

import com.moviebooking.dto.TheaterDto;
import com.moviebooking.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    @Transactional(readOnly = true)
    public List<TheaterDto> getAllTheaters() {
        return theaterRepository.findAll().stream().map(TheaterDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<TheaterDto> getTheatersByCity(String city) {
        return theaterRepository.findByCityIgnoreCase(city).stream().map(TheaterDto::from).toList();
    }
}
