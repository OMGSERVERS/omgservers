package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.ViewDeploymentLobbyAssignmentsRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.ViewDeploymentLobbyAssignmentsResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment.SelectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewDeploymentLobbyAssignmentMethodImpl implements ViewDeploymentLobbyAssignmentMethod {

    final SelectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation
            selectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentLobbyAssignmentsResponse> execute(final ViewDeploymentLobbyAssignmentsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var deploymentId = request.getDeploymentId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            deploymentId)
                    );
                })
                .map(ViewDeploymentLobbyAssignmentsResponse::new);

    }
}
