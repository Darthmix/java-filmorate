package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    private User user;
    @Autowired
    private Validator validator;

    @BeforeEach
    void initialization() {
        user = new User();
        user.setEmail("trololo@mail.ru");
        user.setLogin("TestLogin");
        user.setBirthday(LocalDate.of(1988, 10, 10));
        user.setName("TestName");
    }

    @Test
    void checkCorrectFields() {
        Set<ConstraintViolation<User>> violation = validator.validate(user);
        assertTrue(violation.isEmpty(), "Валидация при корректно заполненных полях не прошла");
    }

    @Test
    void checkEmail() {
        user.setEmail("TROLOLOEmail");
        Set<ConstraintViolation<User>> violation = validator.validate(user);
        assertFalse(violation.isEmpty(), "Валидация прошла при некорректном Email");
        user.setEmail(null);
        assertFalse(violation.isEmpty(), "Валидация прошла при значении null");
    }

    @Test
    void checkLogin() {
        user.setLogin("");
        Set<ConstraintViolation<User>> violation = validator.validate(user);
        assertFalse(violation.isEmpty(), "Валидация прошла при пустом логине");
        user.setLogin(null);
        assertFalse(violation.isEmpty(), "Валидация прошла при значении null");
        user.setLogin("Trololo Trololo");
        assertFalse(violation.isEmpty(), "Валидация прошла при наличии пробела");
    }

    @Test
    void checkBirthday() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<User>> violation = validator.validate(user);
        assertFalse(violation.isEmpty(), "Валидация прошла при дате рождения в будущем");
        user.setBirthday(null);
        assertFalse(violation.isEmpty(), "Валидация прошла при значении null");
    }
}