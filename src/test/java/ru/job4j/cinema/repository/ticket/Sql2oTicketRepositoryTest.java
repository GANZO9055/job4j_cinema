package ru.job4j.cinema.repository.ticket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.user.Sql2oUserRepository;
import ru.job4j.cinema.repository.user.UserRepository;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oTicketRepositoryTest {

    private static Sql2o sql2o;
    private static TicketRepository ticketRepository;
    private static UserRepository userRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        ticketRepository = new Sql2oTicketRepository(sql2o);
        userRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    void clearTickets() {
        try (var connection = sql2o.open()) {
            connection.createQuery("DELETE FROM tickets").executeUpdate();
            connection.createQuery("DELETE FROM users").executeUpdate();
        }
    }

    @Test
    void whenBuyTicketThenGetTicket() {
        var user = userRepository.save(new User("test1", "test1", "test1"));
        Ticket ticket1 = new Ticket(10, 10, 20, user.get().getId());
        var ticket = ticketRepository.buyTicket(ticket1).get();
        assertThat(ticket).usingRecursiveComparison().isEqualTo(ticket1);
    }

    @Test
    void whenFindTicketByIdThenGetTicket() {
        var user = userRepository.save(new User("test2", "test2", "test2"));
        Ticket ticket1 = new Ticket(10, 10, 20, user.get().getId());
        int number = ticketRepository.buyTicket(ticket1).get().getId();
        var ticket = ticketRepository.findById(number).get();
        assertThat(ticket).usingRecursiveComparison().isEqualTo(ticket1);
    }

}