package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film createFilm(Film film) {
        return filmStorage.create(film);
    }

    public Film updateFilm(Film newFilm) throws NotFoundException {
        Film oldFilm = filmStorage.getFilmById(newFilm.getId());
        oldFilm.setName(newFilm.getName());
        oldFilm.setDescription(newFilm.getDescription());
        oldFilm.setDuration(newFilm.getDuration());
        oldFilm.setReleaseDate(newFilm.getReleaseDate());
        return filmStorage.update(oldFilm);
    }

    private void checkUserById(Integer userId) throws NotFoundException {
        if (userStorage.getUserById(userId) == null)
            throw new NotFoundException("Пользователь с указанным id не найден: " + userId);
    }

    public Film addLikeToFilm(Integer filmId, Integer userId) throws NotFoundException {
        checkUserById(userId);
        filmStorage.getFilmById(filmId).addLike(userId);
        return filmStorage.getFilmById(filmId);
    }

    public Film removeLikeFromFilm(Integer filmId, Integer userId) throws NotFoundException {
        checkUserById(userId);
        filmStorage.getFilmById(filmId).removeLike(userId);
        return filmStorage.getFilmById(filmId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getFilms()
                          .stream()
                          .sorted(Comparator.comparingInt(Film::getNumberOfLikes).reversed())
                          .limit(count)
                          .toList();
    }
}
