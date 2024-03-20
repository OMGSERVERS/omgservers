package com.omgservers.service.handler.lobby;

import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.LobbyCreatedEventBodyModel;
import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.factory.VersionLobbyRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.runtime.RuntimeModule;
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
public class LobbyCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final GenerateIdOperation generateIdOperation;

    final VersionLobbyRefModelFactory versionLobbyRefModelFactory;
    final RuntimeModelFactory runtimeModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (LobbyCreatedEventBodyModel) event.getBody();
        final var lobbyId = body.getId();

        return getLobby(lobbyId)
                .flatMap(lobby -> {
                    log.info("Lobby was created, id={}", lobbyId);

                    final var idempotencyKey = event.getIdempotencyKey();

                    return syncRuntime(lobby, idempotencyKey)
                            .flatMap(created -> syncVersionLobbyRef(lobby, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<LobbyModel> getLobby(final Long id) {
        final var request = new GetLobbyRequest(id);
        return lobbyModule.getLobbyService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }

    Uni<Boolean> syncRuntime(final LobbyModel lobby, final String idempotencyKey) {
        final var lobbyId = lobby.getId();
        final var tenantId = lobby.getTenantId();
        final var versionId = lobby.getVersionId();
        final var runtimeId = lobby.getRuntimeId();

        final var runtimeConfig = RuntimeConfigModel.create();
        runtimeConfig.setLobbyConfig(RuntimeConfigModel.LobbyConfig.builder()
                .lobbyId(lobbyId)
                .build());
        final var runtime = runtimeModelFactory.create(runtimeId,
                tenantId,
                versionId,
                RuntimeQualifierEnum.LOBBY,
                generateIdOperation.generateId(),
                runtimeConfig,
                idempotencyKey);

        final var request = new SyncRuntimeRequest(runtime);
        return runtimeModule.getRuntimeService().syncRuntime(request)
                .map(SyncRuntimeResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", runtime, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> syncVersionLobbyRef(final LobbyModel lobby,
                                     final String idempotencyKey) {
        final var tenantId = lobby.getTenantId();
        final var versionId = lobby.getVersionId();
        final var lobbyId = lobby.getId();
        final var versionLobbyRef = versionLobbyRefModelFactory.create(tenantId,
                versionId,
                lobbyId,
                idempotencyKey);
        final var request = new SyncVersionLobbyRefRequest(versionLobbyRef);
        return tenantModule.getVersionService().syncVersionLobbyRef(request)
                .map(SyncVersionLobbyRefResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", versionLobbyRef, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
