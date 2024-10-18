package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.session.FilmSessionService;

@Controller
@RequestMapping("/sessions")
public class FilmSessionController {

    private FilmSessionService filmSessionService;

    public FilmSessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String getFilmSessionById(Model model, @PathVariable("id") int id) {
        var result = filmSessionService.findById(id);
        if (result.isEmpty()) {
            model.addAttribute("message", "Выбранный сеанс не найден.");
            return "errors/404";
        }
        model.addAttribute("filmSession", result.get());
        return "tickets/buy";
    }
}
