package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.module.deployment.deploymentRequest.GetDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.GetDeploymentRequestResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentRequest.SelectDeploymentRequestOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDeploymentRequestMethodImpl implements GetDeploymentRequestMethod {

    final SelectDeploymentRequestOperation selectDeploymentRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentRequestResponse> execute(
            final GetDeploymentRequestRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var deploymentId = request.getDeploymentId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectDeploymentRequestOperation
                            .execute(sqlConnection, shard.shard(), deploymentId, id));
                })
                .map(GetDeploymentRequestResponse::new);
    }
}
