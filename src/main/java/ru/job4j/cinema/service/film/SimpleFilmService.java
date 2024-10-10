package ru.job4j.cinema.service.film;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.film.FilmRepository;

import java.util.Collection;
import java.util.Optional;

public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    public SimpleFilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Collection<Film> findAll() {
        return null;
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.empty();
    }
}
