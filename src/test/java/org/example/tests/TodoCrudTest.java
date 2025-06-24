package org.example.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.model.Todo;
import org.junit.jupiter.api.*;
import org.example.tests.service.TodoApiService;
import org.example.tests.utils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Todo API")
@Feature("API CRUD")
@DisplayName("CRUD Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoCrudTest {

    private final TodoApiService api = new TodoApiService();
    private static Long createdTodoId;

    @Test
    @Order(1)
    @Story("Create TODO")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldCreateTodo() {
        Todo newTodo = TestDataGenerator.randomTodo();
        createdTodoId = newTodo.getId();

        Response response = api.createTodo(newTodo);
        response.then().statusCode(201);

        assertTrue(response.getBody().asString().isEmpty() || response.getBody().asString().isBlank());
    }

    @Test
    @Order(2)
    @Story("GET TODOs")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldGetTodos() {
        Response response = api.getAllTodos();
        response.then().statusCode(200);

        Todo[] todos = response.as(Todo[].class);
        assertTrue(todos.length >= 1);
    }

    @Test
    @Order(3)
    @Story("Update TODO")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldUpdateTodo() {
        Todo updated = TestDataGenerator.updatedTodo(createdTodoId);

        Response response = api.updateTodo(createdTodoId, updated);
        response.then().statusCode(200);
    }

    @Test
    @Order(4)
    @Story("Delete TODO")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldDeleteTodo() {
        Response response = api.deleteTodo(createdTodoId);
        response.then().statusCode(204);
    }
}