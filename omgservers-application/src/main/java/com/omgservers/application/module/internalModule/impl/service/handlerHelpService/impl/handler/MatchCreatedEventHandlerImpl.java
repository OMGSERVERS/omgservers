package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.runtimeModule.RuntimeModule;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeConfigModel;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeModelFactory;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeTypeEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final InternalModule internalModule;
    final RuntimeModule runtimeModule;

    final RuntimeModelFactory runtimeModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();
        // TODO: Detect runtime type
        final var runtime = runtimeModelFactory.create(matchmakerId, matchId, RuntimeConfigModel
                .create(RuntimeTypeEnum.EMBEDDED_LUA));
        final var syncRuntimeInternalRequest = new SyncRuntimeInternalRequest(runtime);
        return runtimeModule.getRuntimeInternalService().syncRuntime(syncRuntimeInternalRequest)
                .replaceWith(true);
    }
}
