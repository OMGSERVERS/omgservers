package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.SyncDeploymentLobbyResourceRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.SyncDeploymentLobbyResourceResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deployment.VerifyDeploymentExistsOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.UpsertDeploymentLobbyResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncDeploymentLobbyResourceMethodImpl implements SyncDeploymentLobbyResourceMethod {

    final VerifyDeploymentExistsOperation verifyDeploymentExistsOperation;
    final UpsertDeploymentLobbyResourceOperation upsertDeploymentLobbyResourceOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncDeploymentLobbyResourceResponse> execute(final ShardModel shardModel,
                                                            final SyncDeploymentLobbyResourceRequest request) {
        log.trace("{}", request);

        final var deploymentLobbyResource = request.getDeploymentLobbyResource();
        final var deploymentId = deploymentLobbyResource.getDeploymentId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> verifyDeploymentExistsOperation
                                .execute(sqlConnection, shardModel.shard(), deploymentId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertDeploymentLobbyResourceOperation
                                                .execute(changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        deploymentLobbyResource);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "deployment does not exist or was deleted, id=" +
                                                        deploymentId);
                                    }
                                })

                )
                .map(ChangeContext::getResult)
                .map(SyncDeploymentLobbyResourceResponse::new);
    }
}
