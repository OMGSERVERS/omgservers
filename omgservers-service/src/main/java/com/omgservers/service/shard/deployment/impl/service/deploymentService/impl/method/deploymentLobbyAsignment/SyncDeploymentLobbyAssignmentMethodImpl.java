package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.SyncDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.SyncDeploymentLobbyAssignmentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deployment.VerifyDeploymentExistsOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment.UpsertDeploymentLobbyAssignmentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncDeploymentLobbyAssignmentMethodImpl implements SyncDeploymentLobbyAssignmentMethod {

    final UpsertDeploymentLobbyAssignmentOperation upsertDeploymentLobbyAssignmentOperation;
    final VerifyDeploymentExistsOperation verifyDeploymentExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncDeploymentLobbyAssignmentResponse> execute(final SyncDeploymentLobbyAssignmentRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var deploymentLobbyAssignment = request.getDeploymentLobbyAssignment();
        final var deploymentId = deploymentLobbyAssignment.getDeploymentId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyDeploymentExistsOperation
                                            .execute(sqlConnection, shard, deploymentId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertDeploymentLobbyAssignmentOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    deploymentLobbyAssignment);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "deployment does not exist or was deleted, id=" +
                                                                    deploymentId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncDeploymentLobbyAssignmentResponse::new);
    }
}
