package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.User.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.User.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.User.UserDto;
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
    public Collection<UserDto> findAll() {
        return userService.getUsers();
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody NewUserRequest user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public UserDto getById(
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UpdateUserRequest newUser) {
        return userService.updateUser(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public UserDto addFriend(
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id друга должен быть положительным числом") @PathVariable("friendId")
            Integer friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public UserDto removeFromFriends(
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id друга должен быть положительным числом") @PathVariable("friendId")
            Integer friendId) {
        return userService.removeFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<UserDto> getFriends(@Positive @PathVariable("id") Integer id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserDto> getCommonFriends(
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("id") Integer id,
            @Positive(message = "Id пользователя должен быть положительным числом") @PathVariable("otherId")
            Integer otherId) {
        return userService.getCommonFriends(id, otherId);
    }


}
