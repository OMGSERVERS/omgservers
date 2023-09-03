package com.omgservers.handler;

import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.tenant.GetStageVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetStageVersionIdShardedResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.module.tenant.TenantModule;
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
    final TenantModule tenantModule;

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
                .flatMap(matchmaker -> getMatch(matchmakerId, matchId)
                        .flatMap(match -> {
                            final var tenantId = matchmaker.getTenantId();
                            final var stageId = matchmaker.getStageId();
                            final var getCurrentVersionIdShardedRequest = new
                                    GetStageVersionIdShardedRequest(tenantId, stageId);
                            return tenantModule.getVersionShardedService()
                                    .getStageVersionId(getCurrentVersionIdShardedRequest)
                                    .map(GetStageVersionIdShardedResponse::getVersionId)
                                    .flatMap(versionId -> {
                                        final var runtimeId = match.getRuntimeId();
                                        // TODO: Detect runtime type
                                        final var runtime = runtimeModelFactory.create(
                                                runtimeId,
                                                tenantId,
                                                stageId,
                                                versionId,
                                                matchmakerId,
                                                matchId,
                                                RuntimeTypeEnum.EMBEDDED_LUA,
                                                RuntimeConfigModel.create());
                                        final var syncRuntimeInternalRequest = new SyncRuntimeShardedRequest(runtime);
                                        return runtimeModule.getRuntimeShardedService()
                                                .syncRuntime(syncRuntimeInternalRequest)
                                                .replaceWith(true);
                                    });
                        })
                );
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerShardedRequest(matchmakerId);
        return matchmakerModule.getMatchmakerShardedService().getMatchmaker(request)
                .map(GetMatchmakerShardedResponse::getMatchmaker);
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchShardedRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerShardedService().getMatch(request)
                .map(GetMatchShardedResponse::getMatch);
    }
}
