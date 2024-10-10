package ru.job4j.cinema.service.user;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> save(User user);
}
