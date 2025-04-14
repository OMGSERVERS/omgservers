package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.GetDeploymentLobbyResourceResponse;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.SelectDeploymentLobbyResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDeploymentLobbyResourceMethodImpl implements GetDeploymentLobbyResourceMethod {

    final SelectDeploymentLobbyResourceOperation selectDeploymentLobbyResourceOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentLobbyResourceResponse> execute(final ShardModel shardModel,
                                                           final GetDeploymentLobbyResourceRequest request) {
        log.trace("{}", request);

        final var shard = shardModel.shard();
        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectDeploymentLobbyResourceOperation
                        .execute(sqlConnection, shard, deploymentId, id))
                .map(GetDeploymentLobbyResourceResponse::new);
    }
}
