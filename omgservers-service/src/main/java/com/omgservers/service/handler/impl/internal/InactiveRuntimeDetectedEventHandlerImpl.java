package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.model.matchmakerCommand.body.DeleteMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyResourceModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.lobby.LobbyShard;
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
public class InactiveRuntimeDetectedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    final TenantLobbyResourceModelFactory tenantLobbyResourceModelFactory;
    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.INACTIVE_RUNTIME_DETECTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (InactiveRuntimeDetectedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    final var runtimeQualifier = runtime.getQualifier();
                    log.warn("Inactive runtime was detected, {}", runtime);

                    final var idempotencyKey = event.getId().toString();

                    return switch (runtimeQualifier) {
                        case LOBBY -> {
                            final var lobbyConfig = runtime.getConfig().getLobbyConfig();
                            final var lobbyId = lobbyConfig.getLobbyId();

                            final var tenantId = runtime.getTenantId();
                            final var deploymentId = runtime.getDeploymentId();

                            yield syncTenantLobbyResource(tenantId, deploymentId, idempotencyKey)
                                    .flatMap(created -> deleteLobby(lobbyId));
                        }
                        case MATCH -> {
                            final var matchConfig = runtime.getConfig().getMatchConfig();
                            final var matchmakerId = matchConfig.getMatchmakerId();
                            final var matchId = matchConfig.getMatchId();
                            yield syncDeleteMatchMatchmakerCommand(matchmakerId, matchId, idempotencyKey);
                        }
                    };
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> deleteLobby(final Long lobbyId) {
        final var request = new DeleteLobbyRequest(lobbyId);
        return lobbyShard.getService().deleteLobby(request)
                .map(DeleteLobbyResponse::getDeleted);
    }

    Uni<Boolean> syncTenantLobbyResource(final Long tenantId,
                                         final Long deploymentId,
                                         final String idempotencyKey) {
        final var tenantLobbyResource = tenantLobbyResourceModelFactory.create(tenantId,
                deploymentId,
                idempotencyKey);
        final var request = new SyncTenantLobbyResourceRequest(tenantLobbyResource);
        return tenantShard.getService().executeWithIdempotency(request)
                .map(SyncTenantLobbyResourceResponse::getCreated);
    }

    Uni<Boolean> syncDeleteMatchMatchmakerCommand(final Long matchmakerId,
                                                  final Long matchId,
                                                  final String idempotencyKey) {
        final var commandBody = new DeleteMatchMatchmakerCommandBodyDto(matchId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId,
                commandBody,
                idempotencyKey);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerShard.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
