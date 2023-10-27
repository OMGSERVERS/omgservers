package com.omgservers.handler;

import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientDisconnectedEventBodyModel;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientDisconnectedEventHandlerImpl implements EventHandler {

    final UserModule userModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_DISCONNECTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ClientDisconnectedEventBodyModel) event.getBody();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var request = new DeleteClientRequest(userId, clientId);

        log.info("Client disconnected, userId={}, clientId={}", userId, clientId);

        return userModule.getClientService().deleteClient(request)
                .replaceWith(true);
    }
}

