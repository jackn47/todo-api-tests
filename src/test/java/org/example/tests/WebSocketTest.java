package org.example.tests;

import io.qameta.allure.*;
import org.example.tests.service.TodoApiService;
import org.example.tests.service.WebSocketClientService;
import org.example.model.Todo;
import org.example.tests.utils.TestDataGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Todo API")
@Feature("WebSocket Notifications")
@DisplayName("WS Notifications Test")
public class WebSocketTest {

    private final TodoApiService api = new TodoApiService();
    private WebSocketClientService wsClient;

    @BeforeEach
    void setup() throws Exception {
        wsClient = new WebSocketClientService();
        wsClient.connect("ws://localhost:8080/ws");
    }

    @AfterEach
    void teardown() {
        wsClient.close();
    }

    @Story("WebSocket todo creation event")
    @Description("Checks that a WS message is received after creating a new todo")
    @Severity(SeverityLevel.CRITICAL)
    @ParameterizedTest
    @MethodSource("todoProvider")
    void shouldReceiveWsNotificationOnCreate(Todo todo) throws InterruptedException {
        api.createTodo(todo);

        String message = wsClient.waitForMessage(5000);
        assertTrue(message.contains("\"type\":\"new_todo\""));
        assertTrue(message.contains("\"id\":" + todo.getId()));
    }

    static Stream<Todo> todoProvider() {
        return Stream.of(
                TestDataGenerator.todoWithText("todo from ws 1"),
                TestDataGenerator.todoWithText("todo from ws 2"),
                TestDataGenerator.todoWithText("todo from ws 3")
        );
    }
}