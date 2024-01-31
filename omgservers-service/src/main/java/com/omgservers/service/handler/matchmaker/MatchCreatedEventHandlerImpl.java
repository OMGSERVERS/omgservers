package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.factory.JobModelFactory;
import com.omgservers.service.factory.MatchClientModelFactory;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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
    final RuntimeModule runtimeModule;
    final SystemModule systemModule;
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
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return matchmakerModule.getShortcutService().getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                        .flatMap(match -> {
                            log.info("Match was created, match={}/{}", matchmakerId, matchId);

                            final var versionId = matchmaker.getVersionId();
                            return syncRuntime(matchmaker, match, versionId)
                                    .flatMap(runtimeWasCreated -> syncMatchJob(match));
                        })
                )
                .replaceWith(true);
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
        final var runtime = runtimeModelFactory.create(
                runtimeId,
                tenantId,
                versionId,
                RuntimeQualifierEnum.MATCH,
                runtimeConfig);
        return runtimeModule.getShortcutService().syncRuntime(runtime);
    }

    Uni<Boolean> syncMatchJob(final MatchModel match) {
        final var shardKey = match.getMatchmakerId();
        final var entityId = match.getId();
        final var job = jobModelFactory.create(shardKey, entityId, JobQualifierEnum.MATCH);
        return systemModule.getShortcutService().syncJob(job);
    }
}
