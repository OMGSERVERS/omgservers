package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentCommand.SyncDeploymentCommandRequest;
import com.omgservers.schema.module.deployment.deploymentCommand.SyncDeploymentCommandResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deployment.VerifyDeploymentExistsOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand.UpsertDeploymentCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncDeploymentCommandMethodImpl implements SyncDeploymentCommandMethod {

    final UpsertDeploymentCommandOperation upsertDeploymentCommandOperation;
    final VerifyDeploymentExistsOperation verifyDeploymentExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncDeploymentCommandResponse> execute(final ShardModel shardModel,
                                                      final SyncDeploymentCommandRequest request) {
        log.trace("{}", request);

        final var deploymentCommand = request.getDeploymentCommand();
        final var deploymentId = deploymentCommand.getDeploymentId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> verifyDeploymentExistsOperation.execute(sqlConnection,
                                        shardModel.shard(),
                                        deploymentId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertDeploymentCommandOperation
                                                .execute(changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        deploymentCommand);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "deployment does not exist or was deleted, id=" +
                                                        deploymentId);
                                    }
                                })

                )
                .map(ChangeContext::getResult)
                .map(SyncDeploymentCommandResponse::new);
    }
}
