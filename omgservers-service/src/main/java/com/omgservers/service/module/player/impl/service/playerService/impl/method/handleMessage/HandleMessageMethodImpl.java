package com.omgservers.service.module.player.impl.service.playerService.impl.method.handleMessage;

import com.omgservers.model.dto.client.HandleMessageRequest;
import com.omgservers.model.dto.client.HandleMessageResponse;
import com.omgservers.model.dto.player.HandleMessagePlayerRequest;
import com.omgservers.model.dto.player.HandleMessagePlayerResponse;
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
class HandleMessageMethodImpl implements HandleMessageMethod {

    final ClientModule clientModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<HandleMessagePlayerResponse> handleMessage(final HandleMessagePlayerRequest request) {
        log.debug("Handle message, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var clientId = request.getClientId();
        final var message = request.getMessage();

        final var handleMessageRequest = new HandleMessageRequest(userId, clientId, message);
        return clientModule.getClientService().handleMessage(handleMessageRequest)
                .map(HandleMessageResponse::getHandled)
                .map(HandleMessagePlayerResponse::new);
    }
}
