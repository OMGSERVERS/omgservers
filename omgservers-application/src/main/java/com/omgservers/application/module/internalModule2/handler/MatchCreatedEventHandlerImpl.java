package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.factory.RuntimeModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.base.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.runtimeModule.RuntimeModule;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
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
        final var runtime = runtimeModelFactory.create(matchmakerId, matchId, RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        final var syncRuntimeInternalRequest = new SyncRuntimeInternalRequest(runtime);
        return runtimeModule.getRuntimeInternalService().syncRuntime(syncRuntimeInternalRequest)
                .replaceWith(true);
    }
}
