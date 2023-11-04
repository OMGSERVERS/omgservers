package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DoSetObjectRequest;
import com.omgservers.model.dto.runtime.DoSetObjectResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.SetObjectRequestedEventBodyModel;
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
public class SetObjectRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SET_OBJECT_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SetObjectRequestedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var object = body.getObject();

        return doSetObject(runtimeId, userId, clientId, object)
                .replaceWith(true);
    }

    Uni<Boolean> doSetObject(final Long runtimeId,
                             final Long userId,
                             final Long clientId,
                             final Object object) {
        final var request = new DoSetObjectRequest(runtimeId, userId, clientId, object);
        return runtimeModule.getDoService().doSetObject(request)
                .map(DoSetObjectResponse::getApproved);
    }
}
