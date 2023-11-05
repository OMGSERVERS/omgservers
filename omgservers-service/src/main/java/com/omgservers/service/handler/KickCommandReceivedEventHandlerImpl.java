package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.KickCommandReceivedEventBodyModel;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class KickCommandReceivedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.KICK_COMMAND_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (KickCommandReceivedEventBodyModel) event.getBody();
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
