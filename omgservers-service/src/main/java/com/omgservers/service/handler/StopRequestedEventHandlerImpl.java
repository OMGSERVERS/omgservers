package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.model.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StopRequestedEventBodyModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.factory.MatchmakerCommandModelFactory;
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
public class StopRequestedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STOP_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (StopRequestedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var reason = body.getReason();

        return doStopRuntime(runtimeId, reason)
                .replaceWith(true);
    }

    Uni<Boolean> doStopRuntime(final Long runtimeId, final String reason) {
        final var doStopRuntimeRequest = new DoStopRuntimeRequest(runtimeId, reason);
        return runtimeModule.getDoService().doStopRuntime(doStopRuntimeRequest)
                .map(DoStopRuntimeResponse::getApproved);
    }
}
