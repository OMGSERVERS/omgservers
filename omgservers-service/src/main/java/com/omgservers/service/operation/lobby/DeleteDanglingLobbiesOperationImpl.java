package com.omgservers.service.operation.lobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDanglingLobbiesOperationImpl implements DeleteDanglingLobbiesOperation {

    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<Void> execute(final Long tenantId,
                             final Long deploymentId) {
        return viewTenantLobbyRefs(tenantId, deploymentId)
                .flatMap(tenantLobbyRefs -> Multi.createFrom().iterable(tenantLobbyRefs)
                        .onItem().transformToUniAndConcatenate(this::handleTenantLobbyRef)
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyRefModel>> viewTenantLobbyRefs(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, deploymentId);
        return tenantShard.getService().viewTenantLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<Void> handleTenantLobbyRef(final TenantLobbyRefModel tenantLobbyRef) {
        final var lobbyId = tenantLobbyRef.getLobbyId();
        return getLobby(lobbyId)
                .flatMap(lobby -> {
                    final var runtimeId = lobby.getRuntimeId();
                    // TODO: count instead of view
                    return viewRuntimeAssignment(runtimeId)
                            .flatMap(runtimeAssignments -> {
                                if (runtimeAssignments.isEmpty()) {
                                    final var minLifetime = getServiceConfigOperation.getServiceConfig()
                                            .runtimes().lobby().minLifetime();
                                    final var lifetime = Duration.between(lobby.getCreated(), Instant.now())
                                            .toSeconds();

                                    if (lifetime > minLifetime) {
                                        return deleteLobby(lobbyId)
                                                .invoke(deleted -> {
                                                    if (deleted) {
                                                        log.info("Dangling lobby \"{}\" was deleted, " +
                                                                        "lifetime was {} seconds",
                                                                lobbyId, lifetime);
                                                    }
                                                });
                                    } else {
                                        return Uni.createFrom().voidItem();
                                    }
                                } else {
                                    return Uni.createFrom().voidItem();
                                }
                            });
                })
                .replaceWithVoid();
    }

    Uni<LobbyModel> getLobby(final Long lobbyId) {
        final var request = new GetLobbyRequest(lobbyId);
        return lobbyShard.getService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }

    Uni<List<RuntimeAssignmentModel>> viewRuntimeAssignment(final Long runtimeId) {
        final var request = new ViewRuntimeAssignmentsRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(ViewRuntimeAssignmentsResponse::getRuntimeAssignments);
    }

    Uni<Boolean> deleteLobby(final Long lobbyId) {
        final var request = new DeleteLobbyRequest(lobbyId);
        return lobbyShard.getService().deleteLobby(request)
                .map(DeleteLobbyResponse::getDeleted);
    }
}
