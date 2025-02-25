package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    @NotNull(message = "Email должен быть заполнен")
    @Email(message = "Email должен иметь правильный формат")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = ".*\\S.*", message = "Логин не может содержать пробелы")
    private String login;
    private String name;
    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            this.name = this.login;
        } else {
            this.name = name;
        }
    }
}
