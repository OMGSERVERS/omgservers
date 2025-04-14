package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.FindDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.FindDeploymentMatchmakerResourceResponse;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.SelectDeploymentMatchmakerResourceByMatchmakerIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindDeploymentMatchmakerResourceMethodImpl implements FindDeploymentMatchmakerResourceMethod {

    final SelectDeploymentMatchmakerResourceByMatchmakerIdOperation selectDeploymentMatchmakerResourceByMatchmakerIdOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindDeploymentMatchmakerResourceResponse> execute(final ShardModel shardModel,
                                                                 final FindDeploymentMatchmakerResourceRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var matchmakerId = request.getMatchmakerId();
        return pgPool.withTransaction(sqlConnection -> selectDeploymentMatchmakerResourceByMatchmakerIdOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                deploymentId,
                                matchmakerId))
                .map(FindDeploymentMatchmakerResourceResponse::new);
    }
}
