package com.omgservers.handler;

import com.omgservers.model.dto.internal.SyncJobRequest;
import com.omgservers.model.dto.internal.SyncJobResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.MatchClientModelFactory;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.JobModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
    final SystemModule systemModule;
    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final MatchClientModelFactory matchClientModelFactory;
    final RuntimeModelFactory runtimeModelFactory;
    final JobModelFactory jobModelFactory;

    final GenerateIdOperation generateIdOperation;

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
                            log.info("Match was created, matchId={}, mode={}, matchmakerId={}",
                                    matchId, match.getConfig().getModeConfig().getName(), matchmakerId);

                            final var versionId = matchmaker.getVersionId();
                            return syncRuntime(matchmaker, match, versionId)
                                    .flatMap(runtimeWasCreated -> syncMatchJob(match));
                        })
                )
                .replaceWith(true);
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId, false);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<Boolean> syncRuntime(final MatchmakerModel matchmaker,
                             final MatchModel match,
                             final Long versionId) {
        final var tenantId = matchmaker.getTenantId();
        final var matchmakerId = matchmaker.getId();
        final var matchId = match.getId();
        final var modeConfig = match.getConfig().getModeConfig();
        final var runtimeId = match.getRuntimeId();
        final var runtimeConfig = new RuntimeConfigModel();
        runtimeConfig.setMatchConfig(new RuntimeConfigModel.MatchConfig(matchmakerId, matchId, modeConfig));
        final var scriptId = generateIdOperation.generateId();
        runtimeConfig.setScriptConfig(new RuntimeConfigModel.ScriptConfig(scriptId));
        final var runtime = runtimeModelFactory.create(
                runtimeId,
                tenantId,
                versionId,
                // TODO: Detect runtime type
                RuntimeTypeEnum.EMBEDDED_MATCH_SCRIPT,
                runtimeConfig);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        return runtimeModule.getRuntimeService().syncRuntime(syncRuntimeRequest)
                .map(SyncRuntimeResponse::getCreated);
    }

    Uni<Boolean> syncMatchJob(final MatchModel match) {
        final var shardKey = match.getMatchmakerId();
        final var entityId = match.getId();
        final var job = jobModelFactory.create(shardKey, entityId, JobQualifierEnum.MATCH);
        final var request = new SyncJobRequest(job);
        return systemModule.getJobService().syncJob(request)
                .map(SyncJobResponse::getCreated);
    }
}
