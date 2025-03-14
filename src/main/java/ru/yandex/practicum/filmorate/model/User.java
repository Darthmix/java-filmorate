package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private final Set<Integer> friends = new HashSet<>();

    public void setLogin(String login) {
        this.login = login;
        if (this.name == null || this.name.isBlank()) {
            this.name = this.login;
        }
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
    }

    public void addFriend(Integer id) {
        if (id.equals(this.id)) {
            throw new ValidationException("Пользователь не может быть другом самому себе");
        }
        friends.add(id);
    }

    public void removeFriend(Integer friendId) {
        friends.remove(friendId);
    }
}
