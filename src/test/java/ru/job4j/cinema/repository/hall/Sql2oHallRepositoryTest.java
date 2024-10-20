package ru.job4j.cinema.repository.hall;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oHallRepositoryTest {

    private static Sql2o sql2o;
    private static HallRepository hallRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        hallRepository = new Sql2oHallRepository(sql2o);
    }

    @Test
    void whenFindHallByIdThenGetHall() {
        var hall = hallRepository.findById(1).get();
        assertThat(hall.getName()).isEqualTo("Кинозал Standard");
    }
}