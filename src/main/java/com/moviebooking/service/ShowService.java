package com.moviebooking.service;

import com.moviebooking.dto.SeatDto;
import com.moviebooking.dto.ShowDto;
import com.moviebooking.entity.Show;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.SeatRepository;
import com.moviebooking.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<ShowDto> getShowsByMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie not found with id: " + movieId);
        }
        return showRepository.findUpcomingByMovieId(movieId).stream().map(ShowDto::from).toList();
    }

    @Transactional(readOnly = true)
    public ShowDto getShowById(Long id) {
        return showRepository.findByIdWithDetails(id)
                .map(ShowDto::from)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<SeatDto> getSeatsForShow(Long showId) {
        Show show = showRepository.findByIdWithDetails(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));

        Set<Long> bookedSeatIds = new HashSet<>(seatRepository.findBookedSeatIdsByShowId(showId));

        return seatRepository.findByScreenIdOrderByRowLabelAscSeatNumberAsc(show.getScreen().getId())
                .stream()
                .map(seat -> SeatDto.from(seat, bookedSeatIds.contains(seat.getId())))
                .toList();
    }
}
