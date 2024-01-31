package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import com.omgservers.model.dto.player.HandleMessagePlayerRequest;
import com.omgservers.model.dto.player.HandleMessagePlayerResponse;
import com.omgservers.model.dto.player.ReceiveMessagesPlayerRequest;
import com.omgservers.model.dto.player.ReceiveMessagesPlayerResponse;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ClientMessageBodyModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.tester.model.TestClientModel;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PlayerApiTester {
    static final AtomicLong idGenerator = new AtomicLong();

    final GetConfigOperation getConfigOperation;

    final ObjectMapper objectMapper;

    public CreateUserPlayerResponse createUser() throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Player"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateUserPlayerRequest()))
                .when().put("/omgservers/player-api/v1/request/create-user");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateUserPlayerResponse.class);
        return response;
    }

    public String createToken(Long userId, String password) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Player"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTokenPlayerRequest(userId, password)))
                .when().put("/omgservers/player-api/v1/request/create-token");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTokenPlayerResponse.class);
        return response.getRawToken();
    }

    public Long createClient(final String token,
                             final Long tenantId,
                             final Long stageId,
                             final String secret)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Player"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateClientPlayerRequest(tenantId, stageId, secret)))
                .when().put("/omgservers/player-api/v1/request/create-client");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateClientPlayerResponse.class);
        return response.getClientId();
    }

    public Boolean sendMessage(TestClientModel testClient, Object data) throws JsonProcessingException {
        final var messageModel = new MessageModel(idGenerator.getAndIncrement(),
                MessageQualifierEnum.CLIENT_MESSAGE,
                new ClientMessageBodyModel(data));
        return handleMessage(testClient, messageModel);
    }

    public Boolean requestMatchmaking(TestClientModel testClient, String mode) throws JsonProcessingException {
        final var messageModel = new MessageModel(idGenerator.getAndIncrement(),
                MessageQualifierEnum.MATCHMAKER_MESSAGE,
                new MatchmakerMessageBodyModel(mode));
        return handleMessage(testClient, messageModel);
    }

    public MessageModel waitMessage(final TestClientModel testClient,
                                    final MessageQualifierEnum messageQualifier)
            throws InterruptedException, JsonProcessingException {
        return waitMessage(testClient, messageQualifier, new ArrayList<>());
    }

    public MessageModel waitMessage(final TestClientModel testClient,
                                    final MessageQualifierEnum messageQualifier,
                                    final List<Long> consumedMessages)
            throws InterruptedException, JsonProcessingException {
        final var maxAttempts = 10;

        int attempt = 0;
        while (attempt < maxAttempts) {
            final var receivedMessages = attempt == 0
                    ? receiveMessages(testClient, consumedMessages)
                    : receiveMessages(testClient, new ArrayList<>());

            final var messageOptional = receivedMessages.stream()
                    .filter(message -> message.getQualifier().equals(messageQualifier))
                    .findFirst();

            if (messageOptional.isPresent()) {
                return messageOptional.get();
            }

            Thread.sleep(1000);
            attempt++;
        }

        throw new IllegalStateException(messageQualifier + " was not consumed");
    }

    List<MessageModel> receiveMessages(final TestClientModel testClient,
                                       final List<Long> consumedMessages)
            throws JsonProcessingException {
        final var token = testClient.getRawToken();
        final var clientId = testClient.getClientId();

        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Player" + testClient.getId()))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new ReceiveMessagesPlayerRequest(clientId, consumedMessages)))
                .when().put("/omgservers/player-api/v1/request/receive-messages");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(ReceiveMessagesPlayerResponse.class);
        return response.getMessages();
    }

    Boolean handleMessage(final TestClientModel testClient,
                          final MessageModel message)
            throws JsonProcessingException {
        final var token = testClient.getRawToken();
        final var clientId = testClient.getClientId();

        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Player" + testClient.getId()))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new HandleMessagePlayerRequest(clientId, message)))
                .when().put("/omgservers/player-api/v1/request/handle-message");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(HandleMessagePlayerResponse.class);
        return response.getHandled();
    }
}
