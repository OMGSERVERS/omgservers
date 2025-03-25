package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.module.deployment.deploymentRequest.ViewDeploymentRequestsRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.ViewDeploymentRequestsResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentRequest.SelectActiveDeploymentRequestsByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewDeploymentRequestsMethodImpl implements ViewDeploymentRequestsMethod {

    final SelectActiveDeploymentRequestsByDeploymentIdOperation selectActiveDeploymentRequestsByDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentRequestsResponse> execute(final ViewDeploymentRequestsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var deploymentId = request.getDeploymentId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveDeploymentRequestsByDeploymentIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            deploymentId));
                })
                .map(ViewDeploymentRequestsResponse::new);
    }
}
