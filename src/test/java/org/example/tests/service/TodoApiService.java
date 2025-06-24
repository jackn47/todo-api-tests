package org.example.tests.service;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.example.model.Todo;

import static io.restassured.RestAssured.given;

public class TodoApiService {
    private final String BASE_URI = "http://localhost:8080";

    static {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Step("Get all todos")
    public Response getAllTodos() {
        return given()
                .baseUri(BASE_URI)
                .when()
                .get("/todos");
    }

    @Step("Create new todo: {todo}")
    public Response createTodo(Todo todo) {
        return given()
                .baseUri(BASE_URI)
                .contentType("application/json")
                .body(todo)
                .when()
                .post("/todos");
    }

    @Step("Update todo by ID {id}")
    public Response updateTodo(Long id, Todo todo) {
        return given()
                .baseUri(BASE_URI)
                .contentType("application/json")
                .body(todo)
                .when()
                .put("/todos/" + id);
    }

    @Step("Delete todo by ID {id}")
    public Response deleteTodo(Long id) {
        return given()
                .baseUri(BASE_URI)
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("admin:admin".getBytes()))
                .when()
                .delete("/todos/" + id);
    }
}