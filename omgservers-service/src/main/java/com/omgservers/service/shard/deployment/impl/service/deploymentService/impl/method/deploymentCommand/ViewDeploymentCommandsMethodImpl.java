package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentCommand.ViewDeploymentCommandsRequest;
import com.omgservers.schema.shard.deployment.deploymentCommand.ViewDeploymentCommandsResponse;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentCommandsResponse> execute(final ShardModel shardModel,
                                                       final ViewDeploymentCommandsRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();

        return pgPool.withTransaction(
                        sqlConnection -> selectActiveDeploymentCommandsByDeploymentIdOperation.execute(sqlConnection,
                                shardModel.shard(),
                                deploymentId
                        ))
                .map(ViewDeploymentCommandsResponse::new);

    }
}
