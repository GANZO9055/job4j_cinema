package ru.job4j.cinema.repository.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.User;

import java.util.Optional;

public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;
    private final Logger logger = LoggerFactory.getLogger(Sql2oUserRepository.class);

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT * FROM users WHERE email = :email AND password = :password"
            );
            query.addParameter("email", email);
            query.addParameter("password", password);
            var result = query.setColumnMappings(User.COLUMN_MAPPING)
                    .executeAndFetchFirst(User.class);
            return Optional.ofNullable(result);
        }
    }

    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var sql = """
                      INSERT INTO users(full_name, email, password)
                      VALUES (:full_name, :email, :password)
                      """;
            var query = connection.createQuery(sql, true)
                    .addParameter("full_name", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception e) {
            logger.error("Данный пользователь уже существует!");
            return Optional.empty();
        }
    }
}
