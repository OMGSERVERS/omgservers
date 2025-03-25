package com.omgservers.service.handler.impl.lobby;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.SyncRuntimeResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.lobby.LobbyCreatedEventBodyModel;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.lobby.LobbyShard;
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
public class LobbyCreatedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    final GenerateIdOperation generateIdOperation;

    final RuntimeModelFactory runtimeModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (LobbyCreatedEventBodyModel) event.getBody();
        final var lobbyId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getLobby(lobbyId)
                .flatMap(lobby -> {
                    log.debug("Created, {}", lobby);

                    final var deploymentId = lobby.getDeploymentId();
                    return getDeployment(deploymentId)
                            .flatMap(deployment -> {
                                final var tenantId = deployment.getTenantId();
                                final var tenantVersionId = deployment.getVersionId();
                                return getTenantVersion(tenantId, tenantVersionId)
                                        .flatMap(tenantVersion -> {
                                            final var tenantVersionConfig = tenantVersion.getConfig();
                                            return createRuntime(lobby, tenantVersionConfig, idempotencyKey);
                                        });
                            });
                })
                .replaceWithVoid();
    }

    Uni<LobbyModel> getLobby(final Long id) {
        final var request = new GetLobbyRequest(id);
        return lobbyShard.getService().execute(request)
                .map(GetLobbyResponse::getLobby);
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

    Uni<Boolean> createRuntime(final LobbyModel lobby,
                               final TenantVersionConfigDto tenantVersionConfig,
                               final String idempotencyKey) {
        final var lobbyId = lobby.getId();
        final var deploymentId = lobby.getDeploymentId();
        final var runtimeId = lobby.getRuntimeId();

        final var runtimeConfig = RuntimeConfigDto.create();
        runtimeConfig.setLobby(new RuntimeConfigDto.LobbyConfigDto(lobbyId));
        runtimeConfig.setVersion(tenantVersionConfig);

        final var runtime = runtimeModelFactory.create(runtimeId,
                deploymentId,
                RuntimeQualifierEnum.LOBBY,
                generateIdOperation.generateId(),
                runtimeConfig,
                idempotencyKey);

        final var request = new SyncRuntimeRequest(runtime);
        return runtimeShard.getService().executeWithIdempotency(request)
                .map(SyncRuntimeResponse::getCreated);
    }
}
