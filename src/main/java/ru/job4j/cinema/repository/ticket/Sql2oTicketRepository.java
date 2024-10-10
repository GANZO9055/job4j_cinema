package ru.job4j.cinema.repository.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;
    private final Logger logger = LoggerFactory.getLogger(Sql2oTicketRepository.class);

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE id = :id");
            query.addParameter("id", id);
            var result = query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(result);
        }
    }

    @Override
    public Optional<Ticket> buyTicket(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                      INSERT INTO tickets(session_id, row_number, place_number, user_id)
                      VALUES (:session_id, :row_number, :place_number, user_id)
                      """;
            var query = connection.createQuery(sql, true)
                    .addParameter("session_id", ticket.getSessionId())
                    .addParameter("row_number", ticket.getRowNumber())
                    .addParameter("place_number", ticket.getPlaceNumber())
                    .addParameter("user_id", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Exception e) {
            logger.error("Данный билет уже куплен!");
            return Optional.empty();
        }
    }
}
