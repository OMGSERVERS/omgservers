package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentRequest.FindDeploymentRequestRequest;
import com.omgservers.schema.shard.deployment.deploymentRequest.FindDeploymentRequestResponse;
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

    final PgPool pgPool;

    @Override
    public Uni<FindDeploymentRequestResponse> execute(final ShardModel shardModel,
                                                      final FindDeploymentRequestRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var clientId = request.getClientId();
        return pgPool.withTransaction(sqlConnection ->
                        selectDeploymentRequestByClientIdOperation
                                .execute(sqlConnection,
                                        shardModel.shard(),
                                        deploymentId,
                                        clientId))
                .map(FindDeploymentRequestResponse::new);
    }
}
