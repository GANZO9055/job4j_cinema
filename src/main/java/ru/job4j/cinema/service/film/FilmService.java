package ru.job4j.cinema.service.film;

import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {
    Collection<Film> findAll();

    Optional<Film> findById(int id);
}
