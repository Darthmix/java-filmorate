package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public List<Film> getFilms() {
        return films.values().stream().toList();
    }

    @Override
    public Film create(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Новый фильм добавлен {} ", film);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        if (!films.containsKey(newFilm.getId())) {
            log.error("Фильм с id: {} не найден", newFilm.getId());
            throw new NotFoundException("Фильм с указанным Id не найден: " + newFilm.getId());
        }
        Film oldFilm = films.replace(newFilm.getId(), newFilm);
        log.info("Данные фильма обновлены {} ", oldFilm);
        return oldFilm;
    }

    @Override
    public Film getFilmById(Integer id) {
        return Optional.ofNullable(films.get(id))
                       .orElseThrow(() -> new NotFoundException("Фильм с указанным Id не найден: " + id));
    }

    private int getNextId() {
        int newId = films.keySet().stream().mapToInt(id -> id).max().orElse(0);
        return ++newId;
    }
}
