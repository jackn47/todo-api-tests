package org.example.tests.service;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WebSocketClientService {

    private WebSocketClient client;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public void connect(String wsUrl) throws Exception {
        client = new WebSocketClient(new URI(wsUrl)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("WebSocket Opened");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Message: " + message);
                messageQueue.offer(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket Closed: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
        client.connectBlocking(); // ожидание подключения
    }

    public void close() {
        if (client != null) {
            client.close();
        }
    }

    public String waitForMessage(long timeoutMillis) throws InterruptedException {
        return messageQueue.poll(timeoutMillis, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
}