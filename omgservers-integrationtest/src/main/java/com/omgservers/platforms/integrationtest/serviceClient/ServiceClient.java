package com.omgservers.platforms.integrationtest.serviceClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import com.omgservers.application.module.gatewayModule.model.message.MessageQualifierEnum;
import com.omgservers.application.module.gatewayModule.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.application.module.gatewayModule.model.message.body.EventMessageBodyModel;
import com.omgservers.application.module.gatewayModule.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.application.module.gatewayModule.model.message.body.SignInMessageBodyModel;
import com.omgservers.application.module.gatewayModule.model.message.body.SignUpMessageBodyModel;
import com.omgservers.platforms.integrationtest.operations.bootstrapVersionOperation.VersionParameters;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;

@Slf4j
public class ServiceClient {

    final ObjectMapper objectMapper;
    final URI uri;

    ServiceEndpoint serviceEndpoint;
    Session session;
    Long userId;
    String password;

    public ServiceClient(ObjectMapper objectMapper, URI uri) throws IOException, DeploymentException {
        this.objectMapper = objectMapper;
        this.uri = uri;

        reconnect();
    }

    public synchronized void reconnect() throws IOException, DeploymentException {
        if (close()) {
            log.info("Reconnect to {}", uri);
        } else {
            log.info("Connect to {}", uri);
        }

        serviceEndpoint = new ServiceEndpoint();
        final var clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        session = ContainerProvider.getWebSocketContainer()
                .connectToServer(serviceEndpoint, clientEndpointConfig, uri);
    }

    public synchronized boolean close() throws IOException {
        if (session != null) {
            log.info("Close connection to {}", uri);
            session.close();
            session = null;
            return true;
        } else {
            return false;
        }
    }

    public synchronized void signUp(VersionParameters versionParameters) throws InterruptedException, IOException {
        final var messageModel = MessageModel.create(MessageQualifierEnum.SIGN_UP_MESSAGE,
                new SignUpMessageBodyModel(versionParameters.getTenantId(),
                        versionParameters.getStageId(), versionParameters.getStageSecret()));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);

        final var credentialsMessage = consumeCredentialsMessage();
        final var credentials = (CredentialsMessageBodyModel) credentialsMessage.getBody();
        userId = credentials.getUserId();
        password = credentials.getPassword();
        log.info("User signed up, userId={}, password={}", userId, password);
    }

    public synchronized void signIn(VersionParameters versionParameters) throws IOException {
        final var messageModel = MessageModel.create(MessageQualifierEnum.SIGN_IN_MESSAGE,
                new SignInMessageBodyModel(versionParameters.getTenantId(),
                        versionParameters.getStageId(), versionParameters.getStageSecret(), userId, password));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);
    }

    public synchronized void requestMatchmaking(String mode) throws IOException {
        final var messageModel = MessageModel
                .create(MessageQualifierEnum.MATCHMAKER_MESSAGE, new MatchmakerMessageBodyModel(mode));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);
    }

    public synchronized EventMessageBodyModel consumeEventMessage() throws InterruptedException, IOException {
        String messageString = serviceEndpoint.receive(45);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.EVENT_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("Event was consumed, {} ", messageModel);
        return (EventMessageBodyModel) messageModel.getBody();
    }

    synchronized MessageModel consumeCredentialsMessage() throws InterruptedException, IOException {
        String messageString = serviceEndpoint.receive(45);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.CREDENTIALS_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("Credentials were consumed, {} ", messageModel);
        return messageModel;
    }

    synchronized void send(String messageString) throws IOException {
        session.getBasicRemote().sendText(messageString);
        log.info("Sent, {}", messageString);
    }
}
