package ru.job4j.cinema.repository.session;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.FilmSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmSessionRepositoryTest {

    private static Sql2o sql2o;
    private static FilmSessionRepository filmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        filmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @Test
    void whenFindAllFilmSessionsThenGetAllIdFilmSessions() {
        var filmSessions = filmSessionRepository.findAll();
        ArrayList<Integer> list = new ArrayList<>();
        for (FilmSession filmSession : filmSessions) {
            list.add(filmSession.getId());
        }
        assertThat(list).isEqualTo(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    void whenFindFilmSessionByIdThenGetFilmSession() {
        var filmSession = filmSessionRepository.findById(1).get();
        assertThat(filmSession.getFilmId()).isEqualTo(1);
    }

}