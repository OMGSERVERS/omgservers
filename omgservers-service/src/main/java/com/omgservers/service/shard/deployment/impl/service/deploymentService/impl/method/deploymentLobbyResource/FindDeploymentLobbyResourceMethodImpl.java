package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.FindDeploymentLobbyResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyResource.FindDeploymentLobbyResourceResponse;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.SelectDeploymentLobbyResourceByLobbyIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindDeploymentLobbyResourceMethodImpl implements FindDeploymentLobbyResourceMethod {

    final SelectDeploymentLobbyResourceByLobbyIdOperation selectDeploymentLobbyResourceByLobbyIdOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindDeploymentLobbyResourceResponse> execute(final ShardModel shardModel,
                                                            final FindDeploymentLobbyResourceRequest request) {
        log.debug("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var lobbyId = request.getLobbyId();
        return pgPool.withTransaction(sqlConnection -> selectDeploymentLobbyResourceByLobbyIdOperation
                        .execute(sqlConnection,
                                shardModel.slot(),
                                deploymentId,
                                lobbyId))
                .map(FindDeploymentLobbyResourceResponse::new);
    }
}
