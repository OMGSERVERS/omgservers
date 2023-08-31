package com.omgservers.utils.testClient;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
class TestEndpoint extends Endpoint {

    BlockingQueue<String> messages;

    TestEndpoint() {
        messages = new LinkedBlockingQueue<>();
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        super.onClose(session, closeReason);
        log.info("Session was closed, sessionId={}", session.getId());
    }

    @Override
    public void onError(Session session, Throwable thr) {
        super.onError(session, thr);
        log.info("Session was failed, sessionId={}, {}", session.getId(), thr.getMessage());
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        log.info("Session was opened, sessionId={}", session.getId());

        session.addMessageHandler(String.class, message -> {
            log.info("Message, sessionId={}, {}", session.getId(), message);
            try {
                messages.put(message);
            } catch (InterruptedException e) {
                log.info("Interrupted, {}", e.getMessage());
            }
        });
    }

    public String receive(long timeout) throws InterruptedException {
        return messages.poll(timeout, TimeUnit.SECONDS);
    }
}
