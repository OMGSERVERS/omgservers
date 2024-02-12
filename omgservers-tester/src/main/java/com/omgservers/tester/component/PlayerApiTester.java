package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import com.omgservers.model.dto.player.InterchangePlayerRequest;
import com.omgservers.model.dto.player.InterchangePlayerResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

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

    public void sendMessage(TestClientModel testClient, Object data) throws JsonProcessingException {
        final var messageModel = new MessageModel(idGenerator.getAndIncrement(),
                MessageQualifierEnum.CLIENT_MESSAGE,
                new ClientMessageBodyModel(data));
        interchange(testClient, Collections.singletonList(messageModel), new ArrayList<>());
    }

    public void requestMatchmaking(TestClientModel testClient, String mode) throws JsonProcessingException {
        final var messageModel = new MessageModel(idGenerator.getAndIncrement(),
                MessageQualifierEnum.MATCHMAKER_MESSAGE,
                new MatchmakerMessageBodyModel(mode));
        interchange(testClient, Collections.singletonList(messageModel), new ArrayList<>());
    }

    public MessageModel waitMessage(final TestClientModel testClient,
                                    final MessageQualifierEnum messageQualifier)
            throws InterruptedException, JsonProcessingException {
        return waitMessage(testClient, message -> message.getQualifier().equals(messageQualifier), new ArrayList<>());
    }

    public MessageModel waitMessage(final TestClientModel testClient,
                                    final Predicate<MessageModel> filter)
            throws InterruptedException, JsonProcessingException {
        return waitMessage(testClient, filter, new ArrayList<>());
    }

    public MessageModel waitMessage(final TestClientModel testClient,
                                    final MessageQualifierEnum messageQualifier,
                                    final List<Long> consumedMessages)
            throws InterruptedException, JsonProcessingException {
        return waitMessage(testClient, message -> message.getQualifier().equals(messageQualifier), consumedMessages);
    }

    public MessageModel waitMessage(final TestClientModel testClient,
                                    final Predicate<MessageModel> filter,
                                    final List<Long> consumedMessages)
            throws InterruptedException, JsonProcessingException {
        final var maxAttempts = 10;

        int attempt = 0;
        while (attempt < maxAttempts) {
            final var receivedMessages = attempt == 0
                    ? interchange(testClient, new ArrayList<>(), consumedMessages)
                    : interchange(testClient, new ArrayList<>(), new ArrayList<>());

            final var messageOptional = receivedMessages.stream()
                    .filter(filter)
                    .findFirst();

            if (messageOptional.isPresent()) {
                return messageOptional.get();
            }

            Thread.sleep(1000);
            attempt++;
        }

        throw new IllegalStateException("Message was not consumed");
    }

    public List<MessageModel> interchange(final TestClientModel testClient,
                                          final List<MessageModel> messagesToHandle,
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
                .body(objectMapper.writeValueAsString(new InterchangePlayerRequest(clientId,
                        messagesToHandle,
                        consumedMessages)))
                .when().put("/omgservers/player-api/v1/request/interchange");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(InterchangePlayerResponse.class);
        return response.getIncomingMessages();
    }
}
