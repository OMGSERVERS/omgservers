package com.omgservers.handler;

import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.factory.RuntimeModelFactory;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.runtime.RuntimeModule;
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

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    final var tenantId = matchmaker.getTenantId();
                    final var stageId = matchmaker.getTenantId();

                    // TODO: Detect runtime type
                    final var runtime = runtimeModelFactory.create(
                            tenantId,
                            stageId,
                            matchmakerId,
                            matchId,
                            RuntimeTypeEnum.EMBEDDED_LUA,
                            RuntimeConfigModel.create());
                    final var syncRuntimeInternalRequest = new SyncRuntimeShardedRequest(runtime);
                    return runtimeModule.getRuntimeShardedService().syncRuntime(syncRuntimeInternalRequest)
                            .replaceWith(true);
                });
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerShardedRequest(matchmakerId);
        return matchmakerModule.getMatchmakerShardedService().getMatchmaker(request)
                .map(GetMatchmakerShardedResponse::getMatchmaker);
    }
}
