package com.omgservers.service.handler.impl.lobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.lobby.LobbyCreatedEventBodyModel;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LobbyCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final GenerateIdOperation generateIdOperation;

    final TenantLobbyRefModelFactory tenantLobbyRefModelFactory;
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

                    final var tenantId = lobby.getTenantId();
                    final var tenantDeploymentId = lobby.getDeploymentId();
                    return getTenantDeployment(tenantId, tenantDeploymentId)
                            .flatMap(tenantDeployment -> {
                                final var deploymentVersionId = tenantDeployment.getVersionId();
                                return getTenantVersion(tenantId, deploymentVersionId)
                                        .flatMap(tenantVersion -> {
                                            final var tenantVersionConfig = tenantVersion
                                                    .getConfig();
                                            return createRuntime(lobby, tenantVersionConfig, idempotencyKey)
                                                    .flatMap(created -> createTenantLobbyRef(lobby, idempotencyKey));
                                        });
                            });
                })
                .replaceWithVoid();
    }

    Uni<LobbyModel> getLobby(final Long id) {
        final var request = new GetLobbyRequest(id);
        return lobbyModule.getService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Boolean> createRuntime(final LobbyModel lobby,
                               final TenantVersionConfigDto tenantVersionConfig,
                               final String idempotencyKey) {
        final var lobbyId = lobby.getId();
        final var tenantId = lobby.getTenantId();
        final var deploymentId = lobby.getDeploymentId();
        final var runtimeId = lobby.getRuntimeId();

        final var runtimeConfig = RuntimeConfigDto.create();
        runtimeConfig.setLobbyConfig(new RuntimeConfigDto.LobbyConfigDto(lobbyId));
        runtimeConfig.setVersionConfig(tenantVersionConfig);

        final var runtime = runtimeModelFactory.create(runtimeId,
                tenantId,
                deploymentId,
                RuntimeQualifierEnum.LOBBY,
                generateIdOperation.generateId(),
                runtimeConfig,
                idempotencyKey);

        final var request = new SyncRuntimeRequest(runtime);
        return runtimeModule.getService().executeWithIdempotency(request)
                .map(SyncRuntimeResponse::getCreated);
    }

    Uni<Boolean> createTenantLobbyRef(final LobbyModel lobby,
                                      final String idempotencyKey) {
        final var tenantId = lobby.getTenantId();
        final var lobbyId = lobby.getId();
        final var deploymentId = lobby.getDeploymentId();
        final var tenantLobbyRef = tenantLobbyRefModelFactory.create(tenantId,
                deploymentId,
                lobbyId,
                idempotencyKey);
        final var request = new SyncTenantLobbyRefRequest(tenantLobbyRef);
        return tenantModule.getService().syncTenantLobbyRefWithIdempotency(request)
                .map(SyncTenantLobbyRefResponse::getCreated);
    }
}
