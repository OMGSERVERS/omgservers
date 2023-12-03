package com.omgservers.tester.component.testClient;

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
    }

    @Override
    public void onError(Session session, Throwable thr) {
        super.onError(session, thr);
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(String.class, message -> {
            try {
                messages.put(message);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        });
    }

    public String receive(long timeout) throws InterruptedException {
        return messages.poll(timeout, TimeUnit.SECONDS);
    }
}
