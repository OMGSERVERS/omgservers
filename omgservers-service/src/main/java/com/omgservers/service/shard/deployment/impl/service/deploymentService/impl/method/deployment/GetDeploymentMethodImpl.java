package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentResponse;
import com.omgservers.service.shard.deployment.impl.operation.deployment.SelectDeploymentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDeploymentMethodImpl implements GetDeploymentMethod {

    final SelectDeploymentOperation selectDeploymentOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentResponse> execute(final ShardModel shardModel,
                                              final GetDeploymentRequest request) {
        log.trace("{}", request);

        final var shard = shardModel.shard();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectDeploymentOperation
                        .execute(sqlConnection, shard, id))
                .map(GetDeploymentResponse::new);
    }
}
