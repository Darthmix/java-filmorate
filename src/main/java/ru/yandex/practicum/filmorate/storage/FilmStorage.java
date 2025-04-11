package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> getFilms();

    Film create(Film film);

    Film update(Film newFilm);

    Film getFilmById(Integer id);

    Film userLikesFilm(Integer id, Integer userId);

    Film deleteLikesFilm(Integer id, Integer userId);

    List<Film> getPopularFilms(int count);
}
