package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerResponse;
import com.omgservers.schema.model.incomingMessage.IncomingMessageModel;
import com.omgservers.schema.shard.client.clientMessage.InterchangeMessagesRequest;
import com.omgservers.schema.shard.client.clientMessage.InterchangeMessagesResponse;
import com.omgservers.service.operation.authz.AuthorizeClientRequestOperation;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InterchangeMessagesMethodImpl implements InterchangeMessagesMethod {

    final ClientShard clientShard;

    final AuthorizeClientRequestOperation authorizeClientRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    @Override
    public Uni<InterchangeMessagesPlayerResponse> execute(final InterchangeMessagesPlayerRequest request) {
        log.debug("Requested, {}", request);

        final var clientId = request.getClientId();
        final var userId = getSecurityAttributeOperation.getUserId();

        return authorizeClientRequestOperation.execute(clientId, userId)
                .flatMap(authorization -> {
                    final var messagesToHandle = request.getOutgoingMessages();
                    final var consumedMessages = request.getConsumedMessages();

                    final var interchangeMessagesRequest = new InterchangeMessagesRequest(clientId,
                            messagesToHandle,
                            consumedMessages);
                    return clientShard.getService().execute(interchangeMessagesRequest)
                            .map(InterchangeMessagesResponse::getIncomingMessages)
                            .map(clientMessages -> {
                                        final var incomingMessages = clientMessages.stream()
                                                .map(clientMessage -> new IncomingMessageModel(clientMessage.getId(),
                                                        clientMessage.getQualifier(), clientMessage.getBody()))
                                                .toList();
                                        return new InterchangeMessagesPlayerResponse(incomingMessages);
                                    }
                            );
                });
    }
}
