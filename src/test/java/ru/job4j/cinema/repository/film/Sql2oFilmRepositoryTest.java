package ru.job4j.cinema.repository.film;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmRepositoryTest {

    private static Sql2o sql2o;
    private static FilmRepository filmRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        filmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    void whenFindAllFilmsThenGetAllIdFilms() {
        var films = filmRepository.findAll();
        ArrayList<Integer> list = new ArrayList<>();
        for (Film film : films) {
            list.add(film.getId());
        }
        assertThat(list).isEqualTo(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    void whenFindFilmByIdThenGetFilm() {
        var film = filmRepository.findById(1).get();
        assertThat(film.getName()).isEqualTo("Трансформеры");
    }
}