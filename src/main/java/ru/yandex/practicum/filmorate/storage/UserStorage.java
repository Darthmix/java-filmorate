package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getUsers();

    User create(User user);

    User update(User newUser);

    User getUserById(Integer id);

    User addFriend(Integer userId, Integer otherId);

    User deleteFriend(Integer userId, Integer friendId);

    List<User> listOfFriends(Integer id);

    List<User> listOfCommonFriends(Integer id, Integer otherId);
}
