package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.ViewDeploymentMatchmakerResourcesRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.ViewDeploymentMatchmakerResourcesResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.SelectActiveDeploymentMatchmakerResourcesByDeploymentIdAndStatusOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.SelectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewDeploymentMatchmakerResourcesMethodImpl implements ViewDeploymentMatchmakerResourcesMethod {

    final SelectActiveDeploymentMatchmakerResourcesByDeploymentIdAndStatusOperation
            selectActiveDeploymentMatchmakerResourcesByDeploymentIdAndStatusOperation;
    final SelectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation
            selectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentMatchmakerResourcesResponse> execute(final ViewDeploymentMatchmakerResourcesRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var deploymentId = request.getDeploymentId();
                    return pgPool.withTransaction(
                            sqlConnection -> {
                                final var status = request.getStatus();
                                if (Objects.nonNull(status)) {
                                    return selectActiveDeploymentMatchmakerResourcesByDeploymentIdAndStatusOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    deploymentId,
                                                    status);
                                } else {
                                    return selectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    deploymentId);
                                }
                            });
                })
                .map(ViewDeploymentMatchmakerResourcesResponse::new);

    }
}
