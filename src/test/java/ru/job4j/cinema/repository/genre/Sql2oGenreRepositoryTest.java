package ru.job4j.cinema.repository.genre;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oGenreRepositoryTest {

    private static Sql2o sql2o;
    private static GenreRepository genreRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oGenreRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        genreRepository = new Sql2oGenreRepository(sql2o);
    }

    @Test
    void whenFindGenreByIdThenGetGenre() {
        var genre = genreRepository.findById(1).get();
        assertThat(genre.getName()).isEqualTo("Комедия");
    }
}