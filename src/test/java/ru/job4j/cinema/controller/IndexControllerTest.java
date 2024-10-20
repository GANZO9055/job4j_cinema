package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IndexControllerTest {

    private IndexController indexController;

    @Test
    void whenRequestIndexThenGetIndex() {
        indexController = new IndexController();
        var result = indexController.getIndex();
        assertThat(result).isEqualTo("index.html");
    }

}