package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.ViewDeploymentMatchmakerResourcesRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.ViewDeploymentMatchmakerResourcesResponse;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentMatchmakerResourcesResponse> execute(final ShardModel shardModel,
                                                                  final ViewDeploymentMatchmakerResourcesRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        return pgPool.withTransaction(
                        sqlConnection -> {
                            final var status = request.getStatus();
                            if (Objects.nonNull(status)) {
                                return selectActiveDeploymentMatchmakerResourcesByDeploymentIdAndStatusOperation
                                        .execute(sqlConnection,
                                                shardModel.slot(),
                                                deploymentId,
                                                status);
                            } else {
                                return selectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation
                                        .execute(sqlConnection,
                                                shardModel.slot(),
                                                deploymentId);
                            }
                })
                .map(ViewDeploymentMatchmakerResourcesResponse::new);

    }
}
