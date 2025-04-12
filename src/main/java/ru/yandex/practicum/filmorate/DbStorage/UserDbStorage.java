package ru.yandex.practicum.filmorate.DbStorage;

import jakarta.validation.ValidationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {

    private static final String INSERT_USER_QUERY = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? " +
                                                    "WHERE user_id = ?";
    private static final String INSERT_FRIEND_QUERY = "INSERT INTO friendship (user_id, friend_id, status) " +
                                                      "VALUES (?, ?, 'UNCONFIRMED')";
    private static final String CONFIRM_FRIEND_QUERY = "UPDATE friendship SET status = 'CONFIRMED' " +
                                                       "WHERE friend_id = ? AND user_id =?";
    private static final String FIND_USERS_QUERY = "SELECT * FROM users";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE user_id = ? CASCADE";
    private static final String DELETE_FRIEND_QUERY = "DELETE FROM friendship WHERE (user_id = ? AND friend_id = ?) " +
                                                      "OR (friend_id = ? AND user_id = ?)";
    private static final String FRIENDS_QUERY = "SELECT f.friend_id FROM friendship AS f WHERE f.user_id = ? " +
                                                "UNION SELECT fr.user_id FROM friendship AS fr WHERE fr.friend_id = ? AND status ='CONFIRMED'";

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<User> getUsers() {
        return findMany(FIND_USERS_QUERY);
    }

    @Override
    public User create(User user) {
        Integer id = insert(INSERT_USER_QUERY,
                            user.getEmail(),
                            user.getLogin(),
                            user.getName(),
                            Date.valueOf(user.getBirthday()));
        user.setId(id);
        return user;
    }

    @Override
    public User update(User newUser) {
        update(UPDATE_USER_QUERY,
               newUser.getEmail(),
               newUser.getLogin(),
               newUser.getName(),
               Date.valueOf(newUser.getBirthday()),
               newUser.getId());
        return newUser;
    }

    @Override
    public User getUserById(Integer userId) {
        User user = findOne(FIND_USER_BY_ID_QUERY, userId).orElseThrow(() -> new NotFoundException(
                "Пользователь не найден, id: " + userId));
        List<Integer> friendsId = jdbc.queryForList(FRIENDS_QUERY, Integer.class, user.getId(), user.getId());
        if (!friendsId.isEmpty()) {
            user.setFriends(Set.copyOf(friendsId));
        }
        return user;
    }

    public void delete(Integer userId) {
        update(DELETE_USER_QUERY, userId);
    }

    @Override
    public List<User> listOfFriends(Integer id) {
        return getUserById(id).getFriends().stream()
                                           .map(this::getUserById)
                                           .toList();
    }

    @Override
    public List<User> listOfCommonFriends(Integer id, Integer otherId) {

        Set<Integer> commonFriends = getUserById(id).getFriends();
        Set<Integer> othersFriends = getUserById(otherId).getFriends();

        return commonFriends.stream()
                            .filter(othersFriends::contains)
                            .map(this::getUserById).toList();
    }

    @Override
    public User addFriend(Integer userId, Integer otherId) {
        if (userId.equals(otherId)) {
            throw new ValidationException("Id друга должен отличаться от id пользователя");
        }
        Set<Integer> userFriends = getUserById(userId).getFriends();
        Set<Integer> otherFriends = getUserById(otherId).getFriends();
        if (otherFriends.contains(userId)) {
            jdbc.update(CONFIRM_FRIEND_QUERY, userId, otherId);
            return getUserById(userId);
        } else if (!userFriends.contains(otherId)) {
            jdbc.update(INSERT_FRIEND_QUERY, userId, otherId);
            return getUserById(userId);
        } else {
            return getUserById(userId);
        }
    }

    @Override
    public User deleteFriend(Integer userId, Integer friendId) {
        Set<Integer> friends = getUserById(userId).getFriends();
        getUserById(friendId);
        if (friends.contains(friendId)) {
            jdbc.update(DELETE_FRIEND_QUERY, userId, friendId, userId, friendId);
        }
        return getUserById(userId);
    }
}
