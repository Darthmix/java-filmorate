package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorResponse handleIncorrectParameter(final NotFoundException exception) {
        log.info("Id не найден: {}", exception.getMessage());
        return new ErrorResponse("Id не найден: ", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleValidationException(final Exception exception) {
        log.info("Ошибка валидации данных. {}", exception.getMessage());
        return new ErrorResponse("Ошибка валидации данных.", exception.getMessage());
    }
}
