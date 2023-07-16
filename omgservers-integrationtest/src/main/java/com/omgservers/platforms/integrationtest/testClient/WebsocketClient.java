package com.omgservers.platforms.integrationtest.testClient;

import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@ClientEndpoint
@ApplicationScoped
public class WebsocketClient {

    BlockingQueue<String> messages;
    Session session;

    public WebsocketClient() {
        messages = new LinkedBlockingQueue<>();
    }

    public void connect(URI uri) throws IOException, DeploymentException {
        if (session != null) {
            session.close();
            messages.clear();
        }
        session = ContainerProvider.getWebSocketContainer()
                .connectToServer(this, uri);
    }

    public void send(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public String receive(long timeout) throws InterruptedException {
        return messages.poll(timeout, TimeUnit.SECONDS);
    }

    public void close() throws IOException {
        session.close();
    }

    @OnOpen
    void onOpen(Session session) {
        log.info("Session was opened");
    }

    @OnClose
    void onClose(Session session) {
        log.info("Session was closed");
    }

    @OnMessage
    void onMessage(String message) {
        log.info("Incoming message, {}", message);
        try {
            messages.put(message);
        } catch (InterruptedException e) {
            log.info("Interrupted, {}", e.getMessage());
        }
    }
}
