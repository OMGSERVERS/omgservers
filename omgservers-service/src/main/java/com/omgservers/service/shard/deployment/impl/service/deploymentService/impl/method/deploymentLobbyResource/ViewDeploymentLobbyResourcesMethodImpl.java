package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.ViewDeploymentLobbyResourcesRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.ViewDeploymentLobbyResourcesResponse;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.SelectActiveDeploymentLobbyResourcesByDeploymentIdAndStatusOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.SelectActiveDeploymentLobbyResourcesByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewDeploymentLobbyResourcesMethodImpl implements ViewDeploymentLobbyResourcesMethod {

    final SelectActiveDeploymentLobbyResourcesByDeploymentIdAndStatusOperation
            selectActiveDeploymentLobbyResourcesByDeploymentIdAndStatusOperation;
    final SelectActiveDeploymentLobbyResourcesByDeploymentIdOperation
            selectActiveDeploymentLobbyResourcesByDeploymentIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewDeploymentLobbyResourcesResponse> execute(final ShardModel shardModel,
                                                             final ViewDeploymentLobbyResourcesRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        return pgPool.withTransaction(
                        sqlConnection -> {
                            final var status = request.getStatus();
                            if (Objects.nonNull(status)) {
                                return selectActiveDeploymentLobbyResourcesByDeploymentIdAndStatusOperation
                                        .execute(sqlConnection,
                                                shardModel.shard(),
                                                deploymentId,
                                                status);
                            } else {
                                return selectActiveDeploymentLobbyResourcesByDeploymentIdOperation
                                        .execute(sqlConnection,
                                                shardModel.shard(),
                                                deploymentId
                                        );
                            }
                        })
                .map(ViewDeploymentLobbyResourcesResponse::new);

    }
}
