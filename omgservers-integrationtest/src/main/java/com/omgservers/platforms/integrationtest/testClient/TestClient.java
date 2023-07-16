package com.omgservers.platforms.integrationtest.testClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import com.omgservers.application.module.gatewayModule.model.message.MessageQualifierEnum;
import com.omgservers.application.module.gatewayModule.model.message.body.EventMessageBodyModel;
import com.omgservers.application.module.gatewayModule.model.message.body.SignInMessageBodyModel;
import com.omgservers.application.module.gatewayModule.model.message.body.SignUpMessageBodyModel;
import com.omgservers.platforms.integrationtest.operations.getConfigOperation.GetConfigOperation;
import jakarta.websocket.DeploymentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TestClient {

    GetConfigOperation getConfigOperation;
    ObjectMapper objectMapper;
    WebsocketClient websocketClient;

    public void connect() throws IOException, DeploymentException {
        final var uri = getConfigOperation.getServers().get(0).externalAddress();
        connect(uri);
    }

    public void connect(final URI serviceUri) throws IOException, DeploymentException {
        var gatewayUri = serviceUri.resolve("/omgservers/gateway");
        websocketClient.connect(gatewayUri);
    }

    public void close() throws IOException {
        websocketClient.close();
    }

    public void signUp(final UUID tenant,
                       final UUID stage,
                       final String secret) throws IOException {
        final var messageModel = MessageModel.create(MessageQualifierEnum.SIGN_UP_MESSAGE,
                new SignUpMessageBodyModel(tenant, stage, secret));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);
    }

    public void signIn(final UUID tenant,
                       final UUID stage,
                       final String secret,
                       final UUID user,
                       final String password) throws IOException {
        final var messageModel = MessageModel.create(MessageQualifierEnum.SIGN_IN_MESSAGE,
                new SignInMessageBodyModel(tenant, stage, secret, user, password));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);
    }

    void send(String messageString) throws IOException {
        websocketClient.send(messageString);
        log.info("Sent, {}", messageString);
    }

    public MessageModel consumeCredentialsMessage() throws InterruptedException, IOException {
        String messageString = websocketClient.receive(30);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.CREDENTIALS_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("Message was consumed, {} ", messageModel);
        return messageModel;
    }

    public EventMessageBodyModel consumeEventMessage() throws InterruptedException, IOException {
        String messageString = websocketClient.receive(30);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.EVENT_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("Message was consumed, {} ", messageModel);
        return (EventMessageBodyModel) messageModel.getBody();
    }
}
