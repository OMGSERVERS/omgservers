package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.FindDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.FindDeploymentLobbyAssignmentResponse;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment.SelectDeploymentLobbyAssignmentByClientIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindDeploymentLobbyAssignmentMethodImpl implements FindDeploymentLobbyAssignmentMethod {

    final SelectDeploymentLobbyAssignmentByClientIdOperation selectDeploymentLobbyAssignmentByClientIdOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindDeploymentLobbyAssignmentResponse> execute(final ShardModel shardModel,
                                                              final FindDeploymentLobbyAssignmentRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        return pgPool.withTransaction(sqlConnection -> {
                    final var clientId = request.getClientId();
                    return selectDeploymentLobbyAssignmentByClientIdOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    deploymentId,
                                    clientId);
                })
                .map(FindDeploymentLobbyAssignmentResponse::new);
    }
}
