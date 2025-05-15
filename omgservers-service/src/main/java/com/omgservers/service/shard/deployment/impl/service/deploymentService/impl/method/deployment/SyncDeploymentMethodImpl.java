package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deployment.SyncDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.SyncDeploymentResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deployment.UpsertDeploymentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncDeploymentMethodImpl implements SyncDeploymentMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertDeploymentOperation upsertDeploymentOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncDeploymentResponse> execute(final ShardModel shardModel,
                                               final SyncDeploymentRequest request) {
        log.debug("{}", request);

        final var deployment = request.getDeployment();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertDeploymentOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                deployment))
                .map(ChangeContext::getResult)
                .map(SyncDeploymentResponse::new);
    }
}
