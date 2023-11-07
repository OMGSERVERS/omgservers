package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DoSetProfileResponse;
import com.omgservers.model.dto.runtime.DoSetProfileRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.SetProfileCommandReceivedEventBodyModel;
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
public class SetProfileCommandReceivedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SET_PROFILE_COMMAND_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (SetProfileCommandReceivedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();
        final var profile = body.getProfile();

        return doSetProfile(runtimeId, userId, clientId, profile)
                .replaceWith(true);
    }

    Uni<Boolean> doSetProfile(final Long runtimeId,
                              final Long userId,
                              final Long clientId,
                              final Object profile) {
        final var request = new DoSetProfileRequest(runtimeId, userId, clientId, profile);
        return runtimeModule.getDoService().doSetProfile(request)
                .map(DoSetProfileResponse::getApproved);
    }
}
