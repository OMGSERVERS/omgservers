package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.FindDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.FindDeploymentMatchmakerAssignmentResponse;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment.SelectDeploymentMatchmakerAssignmentByClientIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindDeploymentMatchmakerAssignmentMethodImpl implements FindDeploymentMatchmakerAssignmentMethod {

    final SelectDeploymentMatchmakerAssignmentByClientIdOperation
            selectDeploymentMatchmakerAssignmentByClientIdOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindDeploymentMatchmakerAssignmentResponse> execute(final ShardModel shardModel,
                                                                   final FindDeploymentMatchmakerAssignmentRequest request) {
        log.debug("{}", request);

        final var deploymentId = request.getDeploymentId();
        return pgPool.withTransaction(sqlConnection -> {
                    final var clientId = request.getClientId();
                    return selectDeploymentMatchmakerAssignmentByClientIdOperation
                            .execute(sqlConnection,
                                    shardModel.slot(),
                                    deploymentId,
                                    clientId);
                })
                .map(FindDeploymentMatchmakerAssignmentResponse::new);
    }
}
