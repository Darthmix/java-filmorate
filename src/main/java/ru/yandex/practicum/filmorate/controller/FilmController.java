package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.Film.FilmDto;
import ru.yandex.practicum.filmorate.dto.Film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.Film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<FilmDto> getAllFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable("id") Integer id) {
        return filmService.getFilmById(id);
    }

    @PostMapping
    public FilmDto createFilm(@Valid @RequestBody NewFilmRequest film) {
        return filmService.createFilm(film);
    }

    @PutMapping
    public FilmDto updateFilm(@Valid @RequestBody UpdateFilmRequest newFilm) {
        return filmService.updateFilm(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public FilmDto addLike(
            @Positive(message = "Id фильма должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("userId")
            Integer userId) {
        return filmService.addLikeToFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmDto removeLike(
            @Positive(message = "Id фильма должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("userId")
            Integer userId) {
        return filmService.removeLikeFromFilm(id, userId);
    }

    @GetMapping("/popular")
    public List<FilmDto> getPopularFilms(@Positive @RequestParam(name = "count", defaultValue = "10") Integer count) {
        return filmService.getPopularFilms(count);
    }
}
