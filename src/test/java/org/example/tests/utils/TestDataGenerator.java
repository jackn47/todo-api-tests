package org.example.tests.utils;

import org.example.model.Todo;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    public static Todo randomTodo() {
        long randomId = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
        return new Todo(
                randomId, "Test TODO " + randomId, false);
    }

    public static Todo updatedTodo(Long id) {
        return new Todo(
                id,
                "Updated todo",
                true
        );
    }

    public static Todo todoWithText(String text) {
        long randomId = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
        return new Todo(randomId, text, false);
    }
}