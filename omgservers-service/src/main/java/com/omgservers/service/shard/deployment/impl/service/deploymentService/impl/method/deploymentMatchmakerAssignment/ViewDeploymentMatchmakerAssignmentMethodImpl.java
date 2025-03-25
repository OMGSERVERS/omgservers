package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(final ViewDeploymentMatchmakerAssignmentsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var deploymentId = request.getDeploymentId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            deploymentId)
                    );
                })
                .map(ViewDeploymentMatchmakerAssignmentsResponse::new);

    }
}
