package com.omgservers.service.handler.matchmaker;

import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerMatchCreatedEventBodyModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchClientModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final MatchmakerMatchClientModelFactory matchmakerMatchClientModelFactory;
    final RuntimeModelFactory runtimeModelFactory;
    final EventModelFactory eventModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMatchCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> getMatch(matchmakerId, matchId)
                        .flatMap(match -> {
                            log.info("Match was created, match={}/{}", matchmakerId, matchId);

                            final var versionId = matchmaker.getVersionId();
                            return syncRuntime(matchmaker, match, versionId, event.getIdempotencyKey());
                        })
                )
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<MatchmakerMatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchmakerMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatch(request)
                .map(GetMatchmakerMatchResponse::getMatchmakerMatch);
    }

    Uni<Boolean> syncRuntime(final MatchmakerModel matchmaker,
                             final MatchmakerMatchModel matchmakerMatch,
                             final Long versionId,
                             final String idempotencyKey) {
        final var tenantId = matchmaker.getTenantId();
        final var matchmakerId = matchmaker.getId();
        final var matchId = matchmakerMatch.getId();
        final var runtimeId = matchmakerMatch.getRuntimeId();
        final var runtimeConfig = new RuntimeConfigDto();
        runtimeConfig.setMatchConfig(new RuntimeConfigDto.MatchConfigDto(matchmakerId, matchId));
        final var runtime = runtimeModelFactory.create(
                runtimeId,
                tenantId,
                versionId,
                RuntimeQualifierEnum.MATCH,
                generateIdOperation.generateId(),
                runtimeConfig,
                idempotencyKey);
        return syncRuntime(runtime);
    }

    Uni<Boolean> syncRuntime(final RuntimeModel runtime) {
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        return runtimeModule.getRuntimeService().syncRuntime(syncRuntimeRequest)
                .map(SyncRuntimeResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", runtime, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
