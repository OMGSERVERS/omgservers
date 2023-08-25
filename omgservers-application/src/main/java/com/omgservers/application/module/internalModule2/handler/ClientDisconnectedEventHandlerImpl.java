package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.dto.userModule.DeleteClientInternalRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientDisconnectedEventBodyModel;
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
        final var request = new DeleteClientInternalRequest(userId, clientId);
        return userModule.getClientInternalService().deleteClient(request)
                .replaceWith(true);
    }
}

