package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.GetDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.GetDeploymentMatchmakerAssignmentResponse;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment.SelectDeploymentMatchmakerAssignmentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDeploymentMatchmakerAssignmentMethodImpl implements GetDeploymentMatchmakerAssignmentMethod {

    final SelectDeploymentMatchmakerAssignmentOperation selectDeploymentMatchmakerAssignmentOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentMatchmakerAssignmentResponse> execute(final ShardModel shardModel,
                                                                  final GetDeploymentMatchmakerAssignmentRequest request) {
        log.trace("{}", request);

        final var slot = shardModel.slot();
        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectDeploymentMatchmakerAssignmentOperation
                        .execute(sqlConnection, slot, deploymentId, id))
                .map(GetDeploymentMatchmakerAssignmentResponse::new);
    }
}
