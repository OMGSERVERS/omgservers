package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand;

import com.omgservers.schema.module.deployment.deploymentCommand.ViewDeploymentCommandsRequest;
import com.omgservers.schema.module.deployment.deploymentCommand.ViewDeploymentCommandsResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand.SelectActiveDeploymentCommandsByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewDeploymentCommandsMethodImpl implements ViewDeploymentCommandsMethod {

    final SelectActiveDeploymentCommandsByDeploymentIdOperation
            selectActiveDeploymentCommandsByDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentCommandsResponse> execute(final ViewDeploymentCommandsRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> pgPool.withTransaction(
                        sqlConnection -> selectActiveDeploymentCommandsByDeploymentIdOperation
                                .execute(sqlConnection,
                                        shard.shard(),
                                        deploymentId
                                )))
                .map(ViewDeploymentCommandsResponse::new);

    }
}
