package ru.job4j.cinema.service.session;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.hall.HallRepository;
import ru.job4j.cinema.repository.session.FilmSessionRepository;
import ru.job4j.cinema.service.film.FilmService;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;
    private final FilmService filmService;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository,
                                    FilmService filmService,
                                    HallRepository hallRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmService = filmService;
        this.hallRepository = hallRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        ConcurrentHashMap<Integer, FilmSessionDto> filmSessionsDto = new ConcurrentHashMap<>();
        for (FilmSession filmSession : filmSessionRepository.findAll()) {
            filmSessionsDto.put(filmSession.getId(),
                    new FilmSessionDto(
                            filmSession,
                            filmService.findById(filmSession.getFilmId()).get(),
                            hallRepository.findById(filmSession.getHallId()).get()
                    ));
        }
        return filmSessionsDto.values();
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        FilmSession filmSession = filmSessionOptional.get();
        return Optional.of(
                new FilmSessionDto(
                        filmSession,
                        filmService.findById(filmSession.getFilmId()).get(),
                        hallRepository.findById(filmSession.getHallId()).get()
        ));
    }
}
