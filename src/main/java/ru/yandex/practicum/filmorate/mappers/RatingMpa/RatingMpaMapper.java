package ru.yandex.practicum.filmorate.mappers.RatingMpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.RatingMpa.RatingMpaDto;
import ru.yandex.practicum.filmorate.model.RatingMpa;

import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingMpaMapper {

    public static RatingMpaDto toDto(RatingMpa mpa) {
        RatingMpaDto dto = new RatingMpaDto();
        dto.setId(mpa.getId());
        dto.setName(mpa.getName());
        return dto;
    }

    public static List<RatingMpaDto> toDto(List<RatingMpa> ratingMpa) {
        return ratingMpa.stream()
                        .map(RatingMpaMapper::toDto)
                        .toList();
    }

    public static RatingMpa fromDto(RatingMpaDto mpaDto) {
        return new RatingMpa(mpaDto.getId(), mpaDto.getName());
    }

    public static List<RatingMpa> fromDto(List<RatingMpaDto> mpaDto) {
        return mpaDto.stream()
                     .map(RatingMpaMapper::fromDto)
                     .toList();
    }

}