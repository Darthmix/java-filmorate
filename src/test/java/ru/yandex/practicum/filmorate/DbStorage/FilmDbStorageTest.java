package ru.yandex.practicum.filmorate.DbStorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.mappers.Film.FilmRowMapper;
import ru.yandex.practicum.filmorate.mappers.Genre.GenreRowMapper;
import ru.yandex.practicum.filmorate.mappers.RatingMpa.RatingMpaRowMapper;
import ru.yandex.practicum.filmorate.mappers.User.UserRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMpa;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class,
        FilmDbStorage.class,
        GenreDbStorage.class,
        RatingMpaDbStorage.class,
        UserRowMapper.class,
        FilmRowMapper.class,
        GenreRowMapper.class,
        RatingMpaRowMapper.class})
public class FilmDbStorageTest {

    private final FilmDbStorage filmsDbStorage;
    public static final Film testFilm = new Film();

    @Autowired
    private DataSource dataSource;

    @BeforeAll
    public static void setTestFilm() {
        testFilm.setName("TestFilm");
        testFilm.setDescription("TestFilm");
        testFilm.setReleaseDate(LocalDate.parse("1919-09-19"));
        testFilm.setDuration(100);
        RatingMpa mpa = new RatingMpa(1, "");
        testFilm.setRatingMpa(mpa);
        Genre genreOne = new Genre(1, "");
        Genre genreTwo = new Genre(3, "");
        testFilm.setGenres(Set.of(genreOne, genreTwo));
    }


    @Test
    public void testGetAllFilms() {
        List<Film> allFilms = filmsDbStorage.getFilms();

        assertThat(allFilms).isNotEmpty();
        assertThat(allFilms).hasSize(5);
    }

    @Test
    public void testGetFilmById() {
        Film film = filmsDbStorage.getFilmById(5);

        assertThat(film).isNotNull();
        assertThat(film.getName()).isEqualTo("Film 5");
    }

    @Test
    public void testCreateFilm() {
        Film addedFilm = filmsDbStorage.create(testFilm);

        assertThat(addedFilm).isNotNull();
        assertThat(addedFilm.getId()).isPositive();
        assertThat(addedFilm.getName()).isEqualTo(testFilm.getName());
    }

    @Test
    public void testUpdateFilm() {
        testFilm.setId(5);
        Film updatedFilm = filmsDbStorage.update(testFilm);

        assertThat(updatedFilm).isNotNull();
        assertThat(updatedFilm.getId()).isEqualTo(5);
        assertThat(updatedFilm.getName()).isEqualTo(testFilm.getName());
    }

    @Test
    public void testUserLikesFilm() {
        Film likedFilm = filmsDbStorage.userLikesFilm(5, 1);

        assertThat(likedFilm).isNotNull();
        assertThat(likedFilm.getId()).isEqualTo(5);

        assertThat(likedFilm.getLikes()).hasSize(1);
        assertThat(likedFilm.getLikes()).contains(1);
    }

    @Test
    public void testDeleteLikesFilm() {
        Film likedFilm = filmsDbStorage.deleteLikesFilm(1, 1);

        assertThat(likedFilm).isNotNull();
        assertThat(likedFilm.getId()).isEqualTo(1);
        assertThat(likedFilm.getLikes()).hasSize(2);
        assertThat(likedFilm.getLikes()).contains(2, 3);
    }

}