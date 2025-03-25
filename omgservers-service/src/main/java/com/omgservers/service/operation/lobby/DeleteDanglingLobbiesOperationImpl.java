//package com.omgservers.service.operation.lobby;
//
//import com.omgservers.schema.model.deploymentLobbyRef.DeploymentLobbyRefModel;
//import com.omgservers.schema.model.lobby.MatchModel;
//import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
//import com.omgservers.schema.module.lobby.DeleteMatchRequest;
//import com.omgservers.schema.module.lobby.DeleteMatchResponse;
//import com.omgservers.schema.module.lobby.GetMatchRequest;
//import com.omgservers.schema.module.lobby.GetMatchResponse;
//import com.omgservers.schema.module.runtime.runtimeAssignment.ViewRuntimeAssignmentsRequest;
//import com.omgservers.schema.module.runtime.runtimeAssignment.ViewRuntimeAssignmentsResponse;
//import com.omgservers.schema.module.deployment.deploymentLobbyRef.ViewDeploymentLobbyRefsRequest;
//import com.omgservers.schema.module.deployment.deploymentLobbyRef.ViewDeploymentLobbyRefsResponse;
//import com.omgservers.service.operation.server.GetServiceConfigOperation;
//import com.omgservers.service.shard.lobby.LobbyShard;
//import com.omgservers.service.shard.runtime.RuntimeShard;
//import com.omgservers.service.shard.tenant.TenantShard;
//import io.smallrye.mutiny.Multi;
//import io.smallrye.mutiny.Uni;
//import jakarta.enterprise.context.ApplicationScoped;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.List;
//
//@Slf4j
//@ApplicationScoped
//@AllArgsConstructor
//class DeleteDanglingLobbiesOperationImpl implements DeleteDanglingLobbiesOperation {
//
//    final RuntimeShard runtimeShard;
//    final TenantShard tenantShard;
//    final LobbyShard lobbyShard;
//
//    final GetServiceConfigOperation getServiceConfigOperation;
//
//    @Override
//    public Uni<Void> execute(final Long tenantId,
//                             final Long poolId) {
//        return viewTenantLobbyRefs(tenantId, poolId)
//                .flatMap(tenantLobbyRefs -> Multi.createFrom().iterable(tenantLobbyRefs)
//                        .onItem().transformToUniAndConcatenate(this::handleTenantLobbyRef)
//                        .collect().asList()
//                )
//                .replaceWithVoid();
//    }
//
//    Uni<List<DeploymentLobbyRefModel>> viewTenantLobbyRefs(final Long tenantId, final Long poolId) {
//        final var request = new ViewDeploymentLobbyRefsRequest(tenantId, poolId);
//        return tenantShard.getService().viewTenantLobbyRefs(request)
//                .map(ViewDeploymentLobbyRefsResponse::getDeploymentLobbyRefs);
//    }
//
//    Uni<Void> handleTenantLobbyRef(final DeploymentLobbyRefModel tenantLobbyRef) {
//        final var lobbyId = tenantLobbyRef.getLobbyId();
//        return getLobby(lobbyId)
//                .flatMap(lobby -> {
//                    final var runtimeId = lobby.getRuntimeId();
//                    // TODO: count instead of view
//                    return viewRuntimeAssignment(runtimeId)
//                            .flatMap(runtimeAssignments -> {
//                                if (runtimeAssignments.isEmpty()) {
//                                    final var minLifetime = getServiceConfigOperation.getServiceConfig()
//                                            .runtimes().lobby().minLifetime();
//                                    final var lifetime = Duration.between(lobby.getCreated(), Instant.now())
//                                            .toSeconds();
//
//                                    if (lifetime > minLifetime) {
//                                        return deleteLobby(lobbyId)
//                                                .invoke(deleted -> {
//                                                    if (deleted) {
//                                                        log.info("Dangling lobby \"{}\" was deleted, " +
//                                                                        "lifetime was {} seconds",
//                                                                lobbyId, lifetime);
//                                                    }
//                                                });
//                                    } else {
//                                        return Uni.createFrom().voidItem();
//                                    }
//                                } else {
//                                    return Uni.createFrom().voidItem();
//                                }
//                            });
//                })
//                .replaceWithVoid();
//    }
//
//    Uni<MatchModel> getLobby(final Long lobbyId) {
//        final var request = new GetMatchRequest(lobbyId);
//        return lobbyShard.getService().execute(request)
//                .map(GetMatchResponse::getLobby);
//    }
//
//    Uni<List<RuntimeAssignmentModel>> viewRuntimeAssignment(final Long runtimeId) {
//        final var request = new ViewRuntimeAssignmentsRequest(runtimeId);
//        return runtimeShard.getService().execute(request)
//                .map(ViewRuntimeAssignmentsResponse::getRuntimeAssignments);
//    }
//
//    Uni<Boolean> deleteLobby(final Long lobbyId) {
//        final var request = new DeleteMatchRequest(lobbyId);
//        return lobbyShard.getService().execute(request)
//                .map(DeleteMatchResponse::getDeleted);
//    }
//}
