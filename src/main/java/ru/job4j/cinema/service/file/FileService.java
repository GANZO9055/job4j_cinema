package ru.job4j.cinema.service.file;

import ru.job4j.cinema.model.File;

import java.util.Optional;

public interface FileService {
    Optional<File> findById(int id);
}
