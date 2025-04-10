package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerResponse;
import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.MessageQualifierEnum;
import com.omgservers.schema.message.body.MessageProducedMessageBodyDto;
import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.tester.dto.TestClientDto;
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
                .when().post("/service/v1/entrypoint/player/request/create-user");
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
                .when().post("/service/v1/entrypoint/player/request/create-token");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTokenPlayerResponse.class);
        return response.getRawToken();
    }

    public Long createClient(final String token,
                             final String tenant,
                             final String tenantProject,
                             final String tenantStage)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Player"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateClientPlayerRequest(tenant,
                        tenantProject,
                        tenantStage)))
                .when().post("/service/v1/entrypoint/player/request/create-client");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateClientPlayerResponse.class);
        return response.getClientId();
    }

    public void sendMessage(TestClientDto testClient, Object message) throws JsonProcessingException {
        final var messageModel = new MessageModel(MessageQualifierEnum.MESSAGE_PRODUCED,
                new MessageProducedMessageBodyDto(message));
        interchange(testClient, Collections.singletonList(messageModel), new ArrayList<>());
    }

    public ClientMessageModel waitMessage(final TestClientDto testClient,
                                    final MessageQualifierEnum messageQualifier)
            throws InterruptedException, JsonProcessingException {
        return waitMessage(testClient, message -> message.getQualifier().equals(messageQualifier), new ArrayList<>());
    }

    public ClientMessageModel waitMessage(final TestClientDto testClient,
                                          final Predicate<ClientMessageModel> filter)
            throws InterruptedException, JsonProcessingException {
        return waitMessage(testClient, filter, new ArrayList<>());
    }

    public ClientMessageModel waitMessage(final TestClientDto testClient,
                                    final MessageQualifierEnum messageQualifier,
                                    final List<Long> consumedMessages)
            throws InterruptedException, JsonProcessingException {
        return waitMessage(testClient, message -> message.getQualifier().equals(messageQualifier), consumedMessages);
    }

    public ClientMessageModel waitMessage(final TestClientDto testClient,
                                          final Predicate<ClientMessageModel> filter,
                                    final List<Long> consumedMessages)
            throws InterruptedException, JsonProcessingException {
        final var maxAttempts = 21;

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

        throw new IllegalStateException("message was not consumed");
    }

    public List<ClientMessageModel> interchange(final TestClientDto testClient,
                                                final List<MessageModel> messagesToHandle,
                                                final List<Long> consumedMessages)
            throws JsonProcessingException {
        final var token = testClient.getRawToken();
        final var clientId = testClient.getClientId();

        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Client" + testClient.getId()))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new InterchangeMessagesPlayerRequest(clientId,
                        messagesToHandle,
                        consumedMessages)))
                .when().post("/service/v1/entrypoint/player/request/interchange");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(InterchangeMessagesPlayerResponse.class);
        return response.getIncomingMessages();
    }
}
