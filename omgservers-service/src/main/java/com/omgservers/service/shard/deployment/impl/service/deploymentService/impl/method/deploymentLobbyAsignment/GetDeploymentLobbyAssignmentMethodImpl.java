package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.GetDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.GetDeploymentLobbyAssignmentResponse;
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

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentLobbyAssignmentResponse> execute(final ShardModel shardModel,
                                                             final GetDeploymentLobbyAssignmentRequest request) {
        log.debug("{}", request);

        final var slot = shardModel.slot();
        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectDeploymentLobbyAssignmentOperation
                        .execute(sqlConnection, slot, deploymentId, id))
                .map(GetDeploymentLobbyAssignmentResponse::new);
    }
}
