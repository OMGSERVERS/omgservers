package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.FindDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.FindDeploymentMatchmakerAssignmentResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindDeploymentMatchmakerAssignmentResponse> execute(final FindDeploymentMatchmakerAssignmentRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var deploymentId = request.getDeploymentId();
                    return pgPool.withTransaction(sqlConnection -> {
                        final var clientId = request.getClientId();
                        return selectDeploymentMatchmakerAssignmentByClientIdOperation
                                .execute(sqlConnection,
                                        shardModel.shard(),
                                        deploymentId,
                                        clientId);
                    });
                })
                .map(FindDeploymentMatchmakerAssignmentResponse::new);
    }
}
