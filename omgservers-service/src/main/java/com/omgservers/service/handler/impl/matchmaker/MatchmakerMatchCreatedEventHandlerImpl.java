package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchCreatedEventBodyModel;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchAssignmentModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;

    final MatchmakerMatchAssignmentModelFactory matchmakerMatchAssignmentModelFactory;
    final RuntimeModelFactory runtimeModelFactory;
    final EventModelFactory eventModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerMatchCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> getMatch(matchmakerId, matchId)
                        .flatMap(match -> {
                            log.debug("Created, {}", match);

                            final var tenantId = matchmaker.getTenantId();
                            final var tenantDeploymentId = matchmaker.getDeploymentId();
                            return getTenantDeployment(tenantId, tenantDeploymentId)
                                    .flatMap(tenantDeployment -> {
                                        final var deploymentVersionId = tenantDeployment.getVersionId();
                                        return getTenantVersion(tenantId, deploymentVersionId)
                                                .flatMap(tenantVersion -> {
                                                    final var tenantVersionConfig = tenantVersion
                                                            .getConfig();
                                                    return createRuntime(matchmaker,
                                                            match,
                                                            tenantVersionConfig,
                                                            event.getIdempotencyKey());
                                                });
                                    });
                        })
                )
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<MatchmakerMatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchmakerMatchRequest(matchmakerId, matchId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerMatchResponse::getMatchmakerMatch);
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantShard.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantShard.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> createRuntime(final MatchmakerModel matchmaker,
                               final MatchmakerMatchModel matchmakerMatch,
                               final TenantVersionConfigDto tenantVersionConfig,
                               final String idempotencyKey) {
        final var tenantId = matchmaker.getTenantId();
        final var matchmakerId = matchmaker.getId();
        final var matchId = matchmakerMatch.getId();
        final var runtimeId = matchmakerMatch.getRuntimeId();

        final var runtimeConfig = new RuntimeConfigDto();
        runtimeConfig.setMatchConfig(new RuntimeConfigDto.MatchConfigDto(matchmakerId, matchId));
        runtimeConfig.setVersionConfig(tenantVersionConfig);

        final var runtime = runtimeModelFactory.create(
                runtimeId,
                tenantId,
                matchmaker.getDeploymentId(),
                RuntimeQualifierEnum.MATCH,
                generateIdOperation.generateId(),
                runtimeConfig,
                idempotencyKey);

        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        return runtimeShard.getService().executeWithIdempotency(syncRuntimeRequest)
                .map(SyncRuntimeResponse::getCreated);
    }
}
