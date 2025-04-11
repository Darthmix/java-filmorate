package ru.yandex.practicum.filmorate.DbStorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.UserDbStorage;
import ru.yandex.practicum.filmorate.mappers.User.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class, UserRowMapper.class})
public class UserDbStorageTest {

    private final UserDbStorage userStorage;
    private static final User testUser = new User();

    @BeforeAll
    public static void setTestUser() {
        testUser.setEmail("test@yandex.ru");
        testUser.setLogin("testLogin");
        testUser.setName("Test Name");
        testUser.setBirthday(LocalDate.of(2000, 1, 1));
    }

    @Test
    public void testGetAllUsers() {
        List<User> allUsers = userStorage.getUsers();

        assertThat(allUsers).isNotEmpty();
        assertThat(allUsers).hasSize(4);
    }

    @Test
    public void testGetUserById() {
        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(1));

        assertThat(userOptional).isPresent()
                                .hasValueSatisfying(user -> assertThat(user).hasFieldOrPropertyWithValue("id", 1));
    }

    @Test
    public void testCreateUser() {
        User addedUser = userStorage.create(testUser);

        assertThat(addedUser).isNotNull();
        assertThat(addedUser.getId()).isPositive();
        assertThat(addedUser.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        testUser.setId(2);
        User updatedUser = userStorage.update(testUser);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(2);
        assertThat(updatedUser.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void testGetAllFriends() {
        List<User> friendsOfUser1 = userStorage.listOfFriends(1);

        assertThat(friendsOfUser1).isNotEmpty();
        assertThat(friendsOfUser1).hasSize(2);
        assertThat(friendsOfUser1).map(User::getId).contains(2, 3);

        List<User> friendsOfUser3 = userStorage.listOfFriends(3);

        assertThat(friendsOfUser3).isNotEmpty();
        assertThat(friendsOfUser3).hasSize(1);
        assertThat(friendsOfUser3).map(User::getId).contains(2);
    }

    @Test
    public void testGetCommonFriends() {
        List<User> commonFriendsFor1and3Users = userStorage.listOfCommonFriends(1, 3);

        assertThat(commonFriendsFor1and3Users).isNotEmpty();
        assertThat(commonFriendsFor1and3Users).hasSize(1);
        assertThat(commonFriendsFor1and3Users).map(User::getId).contains(2);
    }

    @Test
    public void testAddFriend() {
        Set<Integer> friendsIdOfUser1 = userStorage.addFriend(1, 4).getFriends();

        assertThat(friendsIdOfUser1).hasSize(3);
        assertThat(friendsIdOfUser1).contains(2, 3, 4);
    }

    @Test
    public void testDeleteFriend() {
        Set<Integer> friendsIdOfUser3 = userStorage.deleteFriend(3, 2).getFriends();

        assertThat(friendsIdOfUser3).hasSize(0);
    }
}