package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.interchangeMethod;

import com.omgservers.schema.entrypoint.player.InterchangePlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangePlayerResponse;
import com.omgservers.schema.module.client.InterchangeRequest;
import com.omgservers.schema.module.client.InterchangeResponse;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InterchangeMethodImpl implements InterchangeMethod {

    final ClientModule clientModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<InterchangePlayerResponse> execute(final InterchangePlayerRequest request) {
        log.debug("Interchange, request={}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var clientId = request.getClientId();
        final var messagesToHandle = request.getOutgoingMessages();
        final var consumedMessages = request.getConsumedMessages();

        final var interchangeRequest = new InterchangeRequest(userId, clientId, messagesToHandle, consumedMessages);
        return clientModule.getService().interchange(interchangeRequest)
                .map(InterchangeResponse::getIncomingMessages)
                .map(InterchangePlayerResponse::new);
    }
}
