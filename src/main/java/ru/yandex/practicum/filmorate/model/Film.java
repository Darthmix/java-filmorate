package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validators.ValidReleaseDate;


import java.time.LocalDate;


@Data
public class Film {

    private Integer id;
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
    @ValidReleaseDate // кастомный валидатор для проверки даты
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;
}
