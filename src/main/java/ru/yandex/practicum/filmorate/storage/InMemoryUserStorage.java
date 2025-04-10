package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> getUsers() {
        return users.values().stream().toList();
    }

    @Override
    public User create(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Новый пользователь добавлен {}", user);
        return user;
    }

    @Override
    public User update(User newUser) {
        if (!users.containsKey(newUser.getId())) {
            log.error("Пользователь с id: {} не найден", newUser.getId());
            throw new NotFoundException("Пользователь с указанным id не найден: " + newUser.getId());
        }
        User oldUser = users.put(newUser.getId(), newUser);
        log.info("Данные пользователя обновлены {}", oldUser);
        return oldUser;
    }

    @Override
    public User getUserById(Integer id) {
        return Optional.ofNullable(users.get(id))
                       .orElseThrow(() -> new NotFoundException("Пользователь с указанным id не найден: " + id));
    }

    private int getNextId() {
        int newId = users.keySet().stream().mapToInt(id -> id).max().orElse(0);
        return ++newId;
    }
}
