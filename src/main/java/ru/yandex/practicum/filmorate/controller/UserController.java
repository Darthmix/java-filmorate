package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Новый пользователь добавлен {}", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        if (!users.containsKey(newUser.getId())) {
            log.error("Пользователь с id: {} не найден", newUser.getId());
            throw new NotFoundException("Пользователь с указанным id не найден: " + newUser.getId());
        }
        User oldUser = users.get(newUser.getId());
        oldUser.setLogin(newUser.getLogin());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setBirthday(newUser.getBirthday());
        oldUser.setName(newUser.getName());
        log.info("Данные пользователя обновлены {}", oldUser);
        return oldUser;
    }

    private int getNextId() {
        int newId = users.keySet().stream()
            .mapToInt(id -> id)
            .max()
            .orElse(0);
        return ++newId;
    }
}
