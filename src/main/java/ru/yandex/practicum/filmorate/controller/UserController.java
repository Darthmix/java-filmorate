package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userService.getUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        return userService.updateUser(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id друга должен быть положительным числом") @PathVariable("friendId")
            Integer friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFromFriends(
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id друга должен быть положительным числом") @PathVariable("friendId")
            Integer friendId) {
        return userService.removeFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@Positive @PathVariable("id") Integer id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("otherId")
            Integer otherId) {
        return userService.getCommonFriends(id, otherId);
    }


}
