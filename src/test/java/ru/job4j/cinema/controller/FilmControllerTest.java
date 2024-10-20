package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.file.FileService;
import ru.job4j.cinema.service.film.FilmService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {

    private FilmController filmController;
    private FilmService filmService;

    @BeforeEach
    void init() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    void whenAddFilmsDtoListThenGetFilms() {
        var filmDto1 = new FilmDto(1, "test1", "test1", 1, 1, 1, "test1", 1);
        var filmDto2 = new FilmDto(2, "test2", "test2", 2, 2, 2, "test2", 2);
        var list = List.of(filmDto1, filmDto2);
        when(filmService.findAll()).thenReturn(list);

        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var actualList = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualList).isEqualTo(list);
    }
}