package com.omgservers.utils.testClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.AssignmentMessageBodyModel;
import com.omgservers.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.model.message.body.EventMessageBodyModel;
import com.omgservers.model.message.body.MatchMessageBodyModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.model.message.body.SignInMessageBodyModel;
import com.omgservers.model.message.body.SignUpMessageBodyModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.utils.operation.VersionParameters;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;

@Slf4j
public class TestClient {

    final GenerateIdOperation generateIdOperation;
    final ObjectMapper objectMapper;
    final URI uri;

    TestEndpoint testEndpoint;
    Session session;
    Long userId;
    String password;

    public TestClient(GenerateIdOperation generateIdOperation, ObjectMapper objectMapper, URI uri)
            throws IOException, DeploymentException {
        this.generateIdOperation = generateIdOperation;
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

        testEndpoint = new TestEndpoint();
        final var clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        session = ContainerProvider.getWebSocketContainer()
                .connectToServer(testEndpoint, clientEndpointConfig, uri);
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
        final var messageModel =
                new MessageModel(generateIdOperation.generateId(), MessageQualifierEnum.SIGN_UP_MESSAGE,
                        new SignUpMessageBodyModel(versionParameters.getTenantId(),
                                versionParameters.getStageId(), versionParameters.getStageSecret()));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);

        final var credentialsMessageBody = consumeCredentialsMessage();
        userId = credentialsMessageBody.getUserId();
        password = credentialsMessageBody.getPassword();
        log.info("User signed up, userId={}, password={}", userId, password);
    }

    public synchronized void signIn(VersionParameters versionParameters) throws IOException {
        final var messageModel =
                new MessageModel(generateIdOperation.generateId(), MessageQualifierEnum.SIGN_IN_MESSAGE,
                        new SignInMessageBodyModel(versionParameters.getTenantId(),
                                versionParameters.getStageId(), versionParameters.getStageSecret(), userId, password));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);
    }

    public synchronized void requestMatchmaking(String mode) throws IOException {
        final var messageModel =
                new MessageModel(generateIdOperation.generateId(), MessageQualifierEnum.MATCHMAKER_MESSAGE,
                        new MatchmakerMessageBodyModel(mode));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);
    }

    public synchronized void sendMatchMessage(Object data) throws IOException {
        final var messageModel = new MessageModel(generateIdOperation.generateId(), MessageQualifierEnum.MATCH_MESSAGE,
                new MatchMessageBodyModel(data));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        send(messageString);
    }

    public synchronized EventMessageBodyModel consumeEventMessage() throws InterruptedException, IOException {
        String messageString = testEndpoint.receive(450);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.EVENT_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("Event was consumed, {} ", messageModel);
        return (EventMessageBodyModel) messageModel.getBody();
    }

    public synchronized CredentialsMessageBodyModel consumeCredentialsMessage() throws InterruptedException, IOException {
        String messageString = testEndpoint.receive(45);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.CREDENTIALS_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("Credentials message was consumed, {} ", messageModel);
        return (CredentialsMessageBodyModel) messageModel.getBody();
    }

    public synchronized AssignmentMessageBodyModel consumeAssignmentMessage() throws InterruptedException, IOException {
        String messageString = testEndpoint.receive(45);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.ASSIGNMENT_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("Assignment message was consumed, {} ", messageModel);
        return (AssignmentMessageBodyModel) messageModel.getBody();
    }

    synchronized void send(String messageString) throws IOException {
        session.getBasicRemote().sendText(messageString);
        log.info("Sent, {}", messageString);
    }
}
