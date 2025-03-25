package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.SelectDeploymentMatchmakerResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDeploymentMatchmakerResourceMethodImpl implements GetDeploymentMatchmakerResourceMethod {

    final SelectDeploymentMatchmakerResourceOperation selectDeploymentMatchmakerResourceOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentMatchmakerResourceResponse> execute(
            final GetDeploymentMatchmakerResourceRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var deploymentId = request.getDeploymentId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectDeploymentMatchmakerResourceOperation
                            .execute(sqlConnection, shard, deploymentId, id));
                })
                .map(GetDeploymentMatchmakerResourceResponse::new);
    }
}
