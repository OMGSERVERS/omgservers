package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsResponse;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment.SelectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewDeploymentMatchmakerAssignmentMethodImpl implements ViewDeploymentMatchmakerAssignmentMethod {

    final SelectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation
            selectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(final ShardModel shardModel,
                                                                    final ViewDeploymentMatchmakerAssignmentsRequest request) {
        log.debug("{}", request);

        final var deploymentId = request.getDeploymentId();
        return pgPool.withTransaction(
                        sqlConnection -> selectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation
                                .execute(sqlConnection,
                                        shardModel.slot(),
                                        deploymentId)
                )
                .map(ViewDeploymentMatchmakerAssignmentsResponse::new);

    }
}
