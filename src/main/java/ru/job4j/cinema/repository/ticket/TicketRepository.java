package ru.job4j.cinema.repository.ticket;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> findById(int id);

    Optional<Ticket> buyTicket(Ticket ticket);
}
