package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class User {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "full_name", "fullName",
            "email", "email",
            "password", "password"
    );

    private int id;
    private String fullName;
    private String email;
    private String password;

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return id == user.id
                && Objects.equals(fullName, user.fullName)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, password);
    }
}