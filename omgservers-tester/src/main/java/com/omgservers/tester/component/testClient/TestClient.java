package com.omgservers.tester.component.testClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.AssignmentMessageBodyModel;
import com.omgservers.model.message.body.ChangeMessageBodyModel;
import com.omgservers.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.model.message.body.MatchMessageBodyModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.model.message.body.RevocationMessageBodyModel;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.message.body.SignInMessageBodyModel;
import com.omgservers.model.message.body.SignUpMessageBodyModel;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;
import com.omgservers.tester.model.TestVersionModel;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class TestClient {
    static final AtomicLong idGenerator = new AtomicLong();

    final ObjectMapper objectMapper;
    final URI uri;
    final String clientId;

    TestEndpoint testEndpoint;
    Session session;
    Long userId;
    String password;

    public TestClient(ObjectMapper objectMapper, URI uri)
            throws IOException, DeploymentException {
        this.objectMapper = objectMapper;
        this.uri = uri;
        clientId = "WebSocket" + idGenerator.getAndIncrement();

        reconnect();
    }

    public synchronized void reconnect() throws IOException, DeploymentException {
        log.info("{}: Reconnect", clientId);
        close();

        testEndpoint = new TestEndpoint();
        final var clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        session = ContainerProvider.getWebSocketContainer()
                .connectToServer(testEndpoint, clientEndpointConfig, uri);
    }

    public synchronized boolean close() throws IOException {
        log.info("{}: Close", clientId);

        if (session != null) {
            session.close();
            session = null;
            return true;
        } else {
            return false;
        }
    }

    public synchronized void signUp(TestVersionModel testVersion) throws InterruptedException, IOException {
        final var messageModel =
                new MessageModel(idGenerator.getAndIncrement(), MessageQualifierEnum.SIGN_UP_MESSAGE,
                        new SignUpMessageBodyModel(testVersion.getTenantId(),
                                testVersion.getStageId(), testVersion.getStageSecret()));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        log.info("{}: Sign up request, {}", clientId, messageString);
        send(messageString);

        final var credentialsMessageBody = consumeCredentialsMessage();
        userId = credentialsMessageBody.getUserId();
        password = credentialsMessageBody.getPassword();
    }

    public synchronized void signIn(TestVersionModel versionParameters) throws IOException {
        final var messageModel =
                new MessageModel(idGenerator.getAndIncrement(), MessageQualifierEnum.SIGN_IN_MESSAGE,
                        new SignInMessageBodyModel(versionParameters.getTenantId(),
                                versionParameters.getStageId(), versionParameters.getStageSecret(), userId, password));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        log.info("{}: Sign in request, {}", clientId, messageString);
        send(messageString);
    }

    public synchronized void requestMatchmaking(String mode) throws IOException {
        final var messageModel =
                new MessageModel(idGenerator.getAndIncrement(), MessageQualifierEnum.MATCHMAKER_MESSAGE,
                        new MatchmakerMessageBodyModel(mode));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        log.info("{}: Matchmaker request, {}", clientId, messageString);
        send(messageString);
    }

    public synchronized void changeRequest(Object data) throws IOException {
        final var messageModel = new MessageModel(idGenerator.getAndIncrement(), MessageQualifierEnum.CHANGE_MESSAGE,
                new ChangeMessageBodyModel(data));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        log.info("{}: Change request, {}", clientId, messageString);
        send(messageString);
    }

    public synchronized void sendMatchMessage(Object data) throws IOException {
        final var messageModel = new MessageModel(idGenerator.getAndIncrement(), MessageQualifierEnum.MATCH_MESSAGE,
                new MatchMessageBodyModel(data));
        final var messageString = objectMapper.writeValueAsString(messageModel);
        log.info("{}: Match request, {}", clientId, messageString);
        send(messageString);
    }

    public synchronized ServerMessageBodyModel consumeServerMessage() throws InterruptedException, IOException {
        String messageString = testEndpoint.receive(60);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.SERVER_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("{}: Server message received, {}", clientId, messageModel.getBody());
        return (ServerMessageBodyModel) messageModel.getBody();
    }

    public synchronized CredentialsMessageBodyModel consumeCredentialsMessage()
            throws InterruptedException, IOException {
        String messageString = testEndpoint.receive(45);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.CREDENTIALS_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("{}: Credentials message received, {}", clientId, messageModel.getBody());
        return (CredentialsMessageBodyModel) messageModel.getBody();
    }

    public synchronized WelcomeMessageBodyModel consumeWelcomeMessage() throws InterruptedException, IOException {
        String messageString = testEndpoint.receive(45);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.WELCOME_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("{}: Welcome message received, {}", clientId, messageModel.getBody());
        return (WelcomeMessageBodyModel) messageModel.getBody();
    }

    public synchronized AssignmentMessageBodyModel consumeAssignmentMessage() throws InterruptedException, IOException {
        String messageString = testEndpoint.receive(45);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.ASSIGNMENT_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("{}: Assignment message received, {}", clientId, messageModel.getBody());
        return (AssignmentMessageBodyModel) messageModel.getBody();
    }

    public synchronized RevocationMessageBodyModel consumeRevocationMessage() throws InterruptedException, IOException {
        String messageString = testEndpoint.receive(45);
        if (messageString == null) {
            throw new IOException(MessageQualifierEnum.REVOCATION_MESSAGE + " was not received");
        }
        MessageModel messageModel = objectMapper.readValue(messageString, MessageModel.class);
        log.info("{}: Revocation message received, {}", clientId, messageModel.getBody());
        return (RevocationMessageBodyModel) messageModel.getBody();
    }

    synchronized void send(String messageString) throws IOException {
        session.getBasicRemote().sendText(messageString);
    }
}
