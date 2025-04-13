package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.RatingMpa.RatingMpaDto;
import ru.yandex.practicum.filmorate.mappers.RatingMpa.RatingMpaMapper;
import ru.yandex.practicum.filmorate.storage.RatingMpaStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingMpaService {

    private final RatingMpaStorage ratingStorage;

    public RatingMpaDto getRatingById(Integer id) {
        return RatingMpaMapper.toDto(ratingStorage.getRatingMpaById(id));
    }

    public List<RatingMpaDto> getAllRatings() {
        return RatingMpaMapper.toDto(ratingStorage.getRatingMpa());
    }
}