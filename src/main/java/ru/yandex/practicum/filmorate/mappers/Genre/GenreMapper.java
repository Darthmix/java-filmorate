package ru.yandex.practicum.filmorate.mappers.Genre;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.Genre.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreMapper {

    public static GenreDto toDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    public static Set<GenreDto> toDto(Set<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return Set.of();
        }
        return genres.stream()
                     .map(GenreMapper::toDto)
                     .collect(Collectors.toSet());
    }

    public static List<GenreDto> toDto(List<Genre> genres) {
        return genres.stream()
                     .map(GenreMapper::toDto)
                     .toList();
    }

    public static Genre fromDto(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }

    public static Set<Genre> fromDto(Set<GenreDto> genreDto) {
        if (genreDto == null || genreDto.isEmpty()) {
            return Set.of();
        }
        return genreDto.stream()
                       .map(GenreMapper::fromDto)
                       .collect(Collectors.toSet());
    }

    public static List<Genre> fromDto(List<GenreDto> genreDto) {
        return genreDto.stream()
                       .map(GenreMapper::fromDto)
                       .toList();
    }

}