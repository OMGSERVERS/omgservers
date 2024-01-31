package com.omgservers.service.module.player.impl.service.playerService.impl.method.receiveMessages;

import com.omgservers.model.dto.client.ReceiveMessagesRequest;
import com.omgservers.model.dto.client.ReceiveMessagesResponse;
import com.omgservers.model.dto.player.ReceiveMessagesPlayerRequest;
import com.omgservers.model.dto.player.ReceiveMessagesPlayerResponse;
import com.omgservers.service.module.client.ClientModule;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ReceiveMessagesMethodImpl implements ReceiveMessagesMethod {

    final ClientModule clientModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<ReceiveMessagesPlayerResponse> receiveMessages(final ReceiveMessagesPlayerRequest request) {
        log.debug("Receive messages, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var clientId = request.getClientId();
        final var consumedMessages = request.getConsumedMessages();

        final var receiveMessagesRequest = new ReceiveMessagesRequest(userId, clientId, consumedMessages);
        return clientModule.getClientService().receiveMessages(receiveMessagesRequest)
                .map(ReceiveMessagesResponse::getMessages)
                .map(ReceiveMessagesPlayerResponse::new);
    }
}
