package ru.job4j.cinema.service.session;

import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionService {
    Collection<FilmSession> findAll();

    Optional<FilmSession> findById(int id);
}
