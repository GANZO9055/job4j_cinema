package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;

import java.time.LocalDateTime;
import java.util.Objects;

public class FilmSessionDto {

    private int id;
    private FilmDto film;
    private Hall hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public FilmSessionDto(FilmSession filmSession, FilmDto film, Hall hall) {
        this.id = filmSession.getId();
        this.film = film;
        this.hall = hall;
        this.startTime = filmSession.getStartTime();
        this.endTime = filmSession.getEndTime();
        this.price = filmSession.getPrice();
    }

    public FilmSessionDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FilmDto getFilm() {
        return film;
    }

    public void setFilm(FilmDto film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        FilmSessionDto that = (FilmSessionDto) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
