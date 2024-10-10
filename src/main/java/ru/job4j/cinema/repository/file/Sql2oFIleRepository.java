package ru.job4j.cinema.repository.file;

import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;

import java.util.Optional;

public class Sql2oFIleRepository implements FileRepository {

    private final Sql2o sql2o;

    public Sql2oFIleRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<File> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM files WHERE id = :id");
            query.addParameter("id", id);
            var result = query.executeAndFetchFirst(File.class);
            return Optional.ofNullable(result);
        }
    }
}