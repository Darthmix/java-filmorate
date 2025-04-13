package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.Genre.GenreDto;
import ru.yandex.practicum.filmorate.mappers.Genre.GenreMapper;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public GenreDto getGenreById(Integer id) {
        return GenreMapper.toDto(genreStorage.getGenreById(id));
    }

    public List<GenreDto> getGenres() {
        return GenreMapper.toDto(genreStorage.getGenres());
    }
}