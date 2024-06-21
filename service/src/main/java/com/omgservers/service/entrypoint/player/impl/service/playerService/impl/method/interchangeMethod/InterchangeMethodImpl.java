package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.interchangeMethod;

import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import com.omgservers.model.dto.player.InterchangePlayerRequest;
import com.omgservers.model.dto.player.InterchangePlayerResponse;
import com.omgservers.service.module.client.ClientModule;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InterchangeMethodImpl implements InterchangeMethod {

    final ClientModule clientModule;

    final JsonWebToken jwt;

    @Override
    public Uni<InterchangePlayerResponse> interchange(final InterchangePlayerRequest request) {
        log.debug("Interchange, request={}", request);

        final var userId = Long.valueOf(jwt.getClaim(Claims.upn));

        final var clientId = request.getClientId();
        final var messagesToHandle = request.getOutgoingMessages();
        final var consumedMessages = request.getConsumedMessages();

        final var interchangeRequest = new InterchangeRequest(userId, clientId, messagesToHandle, consumedMessages);
        return clientModule.getClientService().interchange(interchangeRequest)
                .map(InterchangeResponse::getIncomingMessages)
                .map(InterchangePlayerResponse::new);
    }
}
