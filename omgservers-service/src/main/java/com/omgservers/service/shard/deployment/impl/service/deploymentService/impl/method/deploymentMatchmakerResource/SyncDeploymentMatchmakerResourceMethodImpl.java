package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.SyncDeploymentMatchmakerResourceResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deployment.VerifyDeploymentExistsOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.UpsertDeploymentMatchmakerResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncDeploymentMatchmakerResourceMethodImpl implements SyncDeploymentMatchmakerResourceMethod {

    final UpsertDeploymentMatchmakerResourceOperation upsertDeploymentMatchmakerResourceOperation;
    final VerifyDeploymentExistsOperation verifyDeploymentExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncDeploymentMatchmakerResourceResponse> execute(final ShardModel shardModel,
                                                                 final SyncDeploymentMatchmakerResourceRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var deploymentMatchmakerResource = request.getDeploymentMatchmakerResource();
        final var deploymentId = deploymentMatchmakerResource.getDeploymentId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> verifyDeploymentExistsOperation
                                .execute(sqlConnection, shardModel.slot(), deploymentId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertDeploymentMatchmakerResourceOperation
                                                .execute(
                                                        changeContext,
                                                        sqlConnection,
                                                        shardModel.slot(),
                                                        deploymentMatchmakerResource);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "deployment does not exist or was deleted, " +
                                                        "id=" + deploymentId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncDeploymentMatchmakerResourceResponse::new);
    }
}
