package ru.job4j.cinema.service.film;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.genre.GenreRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository filmRepository, GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Collection<FilmDto> findAll() {
        ConcurrentHashMap<Integer, FilmDto> filmsDto = new ConcurrentHashMap<>();
        for (Film film : filmRepository.findAll()) {
            filmsDto.put(film.getId(),
                    new FilmDto(
                            film.getId(),
                            film.getName(),
                            film.getDescription(),
                            film.getYear(),
                            film.getMinimalAge(),
                            film.getDurationInMinutes(),
                            genreRepository.findById(film.getGenreId()).get().getName(),
                            film.getFileId()
            ));
        }
        return filmsDto.values();
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        var filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        Film film = filmOptional.get();
        return Optional.of(
                new FilmDto(
                        film.getId(),
                        film.getName(),
                        film.getDescription(),
                        film.getYear(),
                        film.getMinimalAge(),
                        film.getDurationInMinutes(),
                        genreRepository.findById(film.getGenreId()).get().getName(),
                        film.getFileId()
        ));
    }
}
