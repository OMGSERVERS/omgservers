package com.omgservers.service.handler.impl.match;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentResponse;
import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.SyncRuntimeResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.match.MatchCreatedEventBodyModel;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.match.MatchShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final DeploymentShard deploymentShard;
    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;
    final MatchShard matchShard;

    final GenerateIdOperation generateIdOperation;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;
    final RuntimeModelFactory runtimeModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchCreatedEventBodyModel) event.getBody();
        final var matchId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getMatch(matchId)
                .flatMap(match -> {
                    log.debug("Created, {}", match);

                    final var matchmakerId = match.getMatchmakerId();
                    return getMatchmaker(matchmakerId)
                            .flatMap(matchmaker -> {
                                final var deploymentId = matchmaker.getDeploymentId();
                                return getDeployment(deploymentId)
                                        .flatMap(deployment -> {
                                            final var tenantId = deployment.getTenantId();
                                            final var tenantVersionId = deployment.getVersionId();
                                            return getTenantVersion(tenantId, tenantVersionId)
                                                    .flatMap(tenantVersion -> {
                                                        final var tenantVersionConfig = tenantVersion.getConfig();
                                                        final var mode = match.getConfig().getMode();
                                                        return createRuntime(deploymentId,
                                                                match,
                                                                tenantVersionConfig,
                                                                mode,
                                                                idempotencyKey);
                                                    });
                                        });
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchModel> getMatch(final Long id) {
        final var request = new GetMatchRequest(id);
        return matchShard.getService().execute(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<DeploymentModel> getDeployment(final Long deploymentId) {
        final var request = new GetDeploymentRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentResponse::getDeployment);
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionRequest(tenantId, tenantVersionId);
        return tenantShard.getService().execute(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> createRuntime(final Long deploymentId,
                               final MatchModel match,
                               final TenantVersionConfigDto tenantVersionConfig,
                               final String mode,
                               final String idempotencyKey) {
        final var matchmakerId = match.getMatchmakerId();
        final var matchId = match.getId();
        final var runtimeId = match.getRuntimeId();

        final var runtimeConfig = RuntimeConfigDto.create(tenantVersionConfig);
        runtimeConfig.setMatch(new RuntimeConfigDto.MatchConfigDto(matchmakerId, matchId, mode));

        final var runtime = runtimeModelFactory.create(runtimeId,
                deploymentId,
                RuntimeQualifierEnum.MATCH,
                generateIdOperation.generateId(),
                runtimeConfig,
                idempotencyKey);

        final var request = new SyncRuntimeRequest(runtime);
        return runtimeShard.getService().executeWithIdempotency(request)
                .map(SyncRuntimeResponse::getCreated);
    }
}
