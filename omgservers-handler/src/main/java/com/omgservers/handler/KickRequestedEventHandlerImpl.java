package com.omgservers.handler;

import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.KickRequestedEventBodyModel;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class KickRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.KICK_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (KickRequestedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();

        return doKickClient(runtimeId, userId, clientId)
                .replaceWith(true);
    }

    Uni<Boolean> doKickClient(final Long runtimeId, final Long userId, final Long clientId) {
        final var doKickClientRequest = new DoKickClientRequest(runtimeId, userId, clientId);
        return runtimeModule.getDoService().doKickClient(doKickClientRequest)
                .map(DoKickClientResponse::getApproved);
    }
}
