package ru.yandex.practicum.filmorate.mappers.Film;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.Film.FilmDto;
import ru.yandex.practicum.filmorate.dto.Film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.Film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.mappers.Genre.GenreMapper;
import ru.yandex.practicum.filmorate.mappers.RatingMpa.RatingMpaMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmMapper {

    public static Film toEntity(NewFilmRequest request) {
        Film film = new Film();
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());
        film.setRatingMpa(RatingMpaMapper.fromDto(request.getMpa()));
        film.setGenres(GenreMapper.fromDto(request.getGenres()));
        return film;
    }

    public static FilmDto toDto(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setDuration(film.getDuration());
        dto.setMpa(RatingMpaMapper.toDto(film.getRatingMpa()));
        dto.setGenres(GenreMapper.toDto(film.getGenres()));
        return dto;
    }

    public static List<FilmDto> toDto(List<Film> films) {
        return films.stream()
                    .map(FilmMapper::toDto)
                    .toList();
    }

    public static Film toEntity(UpdateFilmRequest request) {
        Film film = new Film();
        film.setId(request.getId());
        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());
        film.setRatingMpa(RatingMpaMapper.fromDto(request.getMpa()));
        film.setGenres(GenreMapper.fromDto(request.getGenres()));
        return film;
    }
}
