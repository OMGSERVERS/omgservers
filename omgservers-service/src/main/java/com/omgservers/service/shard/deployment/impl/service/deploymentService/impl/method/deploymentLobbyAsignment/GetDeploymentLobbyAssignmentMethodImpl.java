package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.GetDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.GetDeploymentLobbyAssignmentResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment.SelectDeploymentLobbyAssignmentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDeploymentLobbyAssignmentMethodImpl implements GetDeploymentLobbyAssignmentMethod {

    final SelectDeploymentLobbyAssignmentOperation selectDeploymentLobbyAssignmentOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentLobbyAssignmentResponse> execute(final GetDeploymentLobbyAssignmentRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var deploymentId = request.getDeploymentId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectDeploymentLobbyAssignmentOperation
                            .execute(sqlConnection, shard, deploymentId, id));
                })
                .map(GetDeploymentLobbyAssignmentResponse::new);
    }
}
