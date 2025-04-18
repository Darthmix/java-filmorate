package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.RatingMpa.RatingMpaDto;
import ru.yandex.practicum.filmorate.service.RatingMpaService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/mpa")
public class RatingMpaController {
    private final RatingMpaService ratingService;

    @GetMapping
    public List<RatingMpaDto> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public RatingMpaDto getRatingById(@PathVariable("id") @Positive Integer id) {
        return ratingService.getRatingById(id);
    }
}