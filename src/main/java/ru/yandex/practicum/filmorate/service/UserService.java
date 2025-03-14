package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        return userStorage.create(user);
    }

    public User updateUser(User newUser) {
        return userStorage.update(newUser);
    }

    public User addFriend(Integer id, Integer friendId) {
        User user = userStorage.getUserById(id);
        user.addFriend(friendId);
        userStorage.getUserById(friendId).addFriend(id);
        return user;
    }

    public User removeFromFriends(Integer id, Integer friendId) {
        User user = userStorage.getUserById(id);
        user.removeFriend(friendId);
        userStorage.getUserById(friendId).removeFriend(id);
        return user;
    }

    public List<User> getFriends(Integer id) {
        return userStorage.getUserById(id).getFriends().stream().map(userStorage::getUserById).toList();
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        Set<Integer> firstUserFriends = userStorage.getUserById(id).getFriends();

        return userStorage.getUserById(otherId)
                          .getFriends()
                          .stream()
                          .filter(firstUserFriends::contains)
                          .map(userStorage::getUserById)
                          .toList();
    }
}
