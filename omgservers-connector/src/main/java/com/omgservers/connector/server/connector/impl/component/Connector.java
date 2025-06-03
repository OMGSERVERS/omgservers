package com.omgservers.connector.server.connector.impl.component;

import com.omgservers.connector.server.handler.component.ConnectorConnection;
import com.omgservers.schema.message.MessageModel;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ToString
public class Connector {

    @Getter
    final ConnectorConnection connection;

    final List<MessageModel> outgoingMessages;

    final List<Long> consumedMessages;

    public Connector(ConnectorConnection connection) {
        this.connection = connection;
        outgoingMessages = new ArrayList<>();
        consumedMessages = new ArrayList<>();
    }

    public synchronized void addOutgoingMessage(final MessageModel message) {
        outgoingMessages.add(message);
    }

    public synchronized List<MessageModel> pullOutgoingMessages() {
        final var result = outgoingMessages.stream().toList();
        outgoingMessages.clear();
        return result;
    }

    public synchronized void addConsumedMessage(final Long id) {
        consumedMessages.add(id);
    }

    public synchronized List<Long> pullConsumedMessages() {
        final var result = consumedMessages.stream().toList();
        consumedMessages.clear();
        return result;
    }
}
