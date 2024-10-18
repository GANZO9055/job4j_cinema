package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.ticket.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public String buyTicket(@ModelAttribute Ticket ticket, Model model) {
        var result = ticketService.buyTicket(ticket);
        if (result.isEmpty()) {
            model.addAttribute("message", "Ошибка! Возможно, билет уже куплен на заданное место. Попробуйте выбрать другое место.");
            return "errors/404";
        }
        model.addAttribute("message",
                String.format("Билет успешно приобретен на место (ряд, место) с номерами: %d, %d",
                        ticket.getRowNumber(),
                        ticket.getPlaceNumber()
                )
        );
        return "tickets/buy";
    }
}
