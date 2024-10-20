package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.ticket.TicketService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private TicketService ticketService;
    private TicketController controller;

    @BeforeEach
    void init() {
        ticketService = mock(TicketService.class);
        controller = new TicketController(ticketService);
    }

    @Test
    void whenSuccessBuyTicketThenGetMessageSuccess() {
        var ticket = new Ticket(1, 1, 1, 1);
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        var expectedMessage = "Билет успешно приобретен на место (ряд, место) с номерами: 1, 1";
        when(ticketService.buyTicket(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = controller.buyTicket(ticket, model);
        var actualMessage = model.getAttribute("message");
        var actualTicket = ticketArgumentCaptor.getValue();

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat(actualTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    void whenFailureBuyTicketThenGetMessageFailure() {
        var ticket = new Ticket(1, 1, 1, 1);
        var expectedMessage = "Ошибка! Возможно, билет уже куплен на заданное место. Попробуйте выбрать другое место.";
        when(ticketService.buyTicket(ticket)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = controller.buyTicket(ticket, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("tickets/failure");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}