package com.omgservers.handler;

import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.SetAttributesRequestedEventBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
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
public class SetAttributesRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SET_ATTRIBUTES_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SetAttributesRequestedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var attributes = body.getAttributes();

        return doSetAttributes(runtimeId, userId, clientId, attributes)
                .replaceWith(true);
    }

    Uni<Boolean> doSetAttributes(final Long runtimeId,
                                 final Long userId,
                                 final Long clientId,
                                 final PlayerAttributesModel attributes) {
        final var request = new DoSetAttributesRequest(runtimeId, userId, clientId, attributes);
        return runtimeModule.getDoService().doSetAttributes(request)
                .map(DoSetAttributesResponse::getApproved);
    }
}
