package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.module.deployment.deploymentRequest.FindDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.FindDeploymentRequestResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentRequest.SelectDeploymentRequestByClientIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindDeploymentRequestMethodImpl implements FindDeploymentRequestMethod {

    final SelectDeploymentRequestByClientIdOperation
            selectDeploymentRequestByClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindDeploymentRequestResponse> execute(final FindDeploymentRequestRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var deploymentId = request.getDeploymentId();
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectDeploymentRequestByClientIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            deploymentId,
                                            clientId));
                })
                .map(FindDeploymentRequestResponse::new);
    }
}
