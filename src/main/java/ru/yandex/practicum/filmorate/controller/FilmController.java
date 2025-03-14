package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        return filmService.updateFilm(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(
            @Positive(message = "Id фильма должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("userId")
            Integer userId) {
        return filmService.addLikeToFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(
            @Positive(message = "Id фильма должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("userId")
            Integer userId) {
        return filmService.removeLikeFromFilm(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@Positive @RequestParam(name = "count", defaultValue = "10") Integer count) {
        return filmService.getPopularFilms(count);
    }
}
