package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmTest {

    private static final LocalDate BIRTHDAY_OF_CINEMA = LocalDate.of(1895, 12, 28);

    private Film film;

    @Autowired
    private Validator validator;

    @BeforeEach
    void initialization(){
        film = new Film();
        film.setId(1);
        film.setName("TestFilmName");
        film.setDescription("TestFilmDescription");
        film.setReleaseDate(LocalDate.of(2025, 2, 23));
        film.setDuration(4);
    }

    @Test
    void checkCorrectFields() {
        Set<ConstraintViolation<Film>> violation = validator.validate(film);
        assertTrue(violation.isEmpty(), "Валидация при корректно заполненных полях не прошла");
    }

    @Test
    void checkDescriptionFilm(){
        film.setDescription("TROLOLO".repeat(2000));
        Set<ConstraintViolation<Film>> violation = validator.validate(film);
        assertFalse(violation.isEmpty(), "Валидация прошла при превышении лимита символов");
    }

    @Test
    void checkNameFilm() {
        film.setName("");
        Set<ConstraintViolation<Film>> violation = validator.validate(film);
        assertFalse(violation.isEmpty(), "Валидация прошла при пустом поле");
        film.setName(null);
        assertFalse(violation.isEmpty(), "Валидация прошла при значении null");
    }

    @Test
    void checkDuration() {
        film.setDuration(-200);
        Set<ConstraintViolation<Film>> violation = validator.validate(film);
        assertFalse(violation.isEmpty(), "Валидация прошла при неположительном значении");
        film.setDuration(0);
        assertFalse(violation.isEmpty(), "Валидация прошла при значении null");
    }

    @Test
    void checkReleaseDate() {
        film.setReleaseDate(BIRTHDAY_OF_CINEMA.minusDays(1));
        Set<ConstraintViolation<Film>> violation = validator.validate(film);
        assertFalse(violation.isEmpty(), "Валидация прошла при дате раньше заданного предела");
        film.setReleaseDate(null);
        assertFalse(violation.isEmpty(), "Валидация прошла при значении null");
    }
}