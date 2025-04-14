package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.SyncDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.SyncDeploymentMatchmakerAssignmentResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deployment.VerifyDeploymentExistsOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment.UpsertDeploymentMatchmakerAssignmentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncDeploymentMatchmakerAssignmentMethodImpl implements SyncDeploymentMatchmakerAssignmentMethod {

    final UpsertDeploymentMatchmakerAssignmentOperation upsertDeploymentMatchmakerAssignmentOperation;
    final VerifyDeploymentExistsOperation verifyDeploymentExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncDeploymentMatchmakerAssignmentResponse> execute(final ShardModel shardModel,
                                                                   final SyncDeploymentMatchmakerAssignmentRequest request) {
        log.trace("{}", request);

        final var deploymentMatchmakerAssignment = request.getDeploymentMatchmakerAssignment();
        final var deploymentId = deploymentMatchmakerAssignment.getDeploymentId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> verifyDeploymentExistsOperation
                                .execute(sqlConnection, shardModel.shard(), deploymentId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertDeploymentMatchmakerAssignmentOperation
                                                .execute(changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        deploymentMatchmakerAssignment);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "deployment does not exist or was deleted, id=" +
                                                        deploymentId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncDeploymentMatchmakerAssignmentResponse::new);
    }
}
