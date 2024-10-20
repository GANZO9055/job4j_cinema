package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.film.FilmService;
import ru.job4j.cinema.service.session.FilmSessionService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {

    private FilmSessionController filmSessionController;
    private FilmSessionService filmSessionService;

    @BeforeEach
    void init() {
        filmSessionService = mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    void whenAddFilmSessionsDtoListThenGetFilmSessions() {
        var filmSessionDto1 = mock(FilmSessionDto.class);
        var filmSessionDto2 = mock(FilmSessionDto.class);
        var list = List.of(filmSessionDto1, filmSessionDto2);
        when(filmSessionService.findAll()).thenReturn(list);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualList = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(actualList).isEqualTo(list);
    }
}