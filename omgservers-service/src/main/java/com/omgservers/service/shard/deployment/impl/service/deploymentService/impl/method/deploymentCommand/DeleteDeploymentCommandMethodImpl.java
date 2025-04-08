package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentCommand.DeleteDeploymentCommandRequest;
import com.omgservers.schema.module.deployment.deploymentCommand.DeleteDeploymentCommandResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand.DeleteDeploymentCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteDeploymentCommandMethodImpl implements DeleteDeploymentCommandMethod {

    final DeleteDeploymentCommandOperation deleteDeploymentCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteDeploymentCommandResponse> execute(final ShardModel shardModel,
                                                        final DeleteDeploymentCommandRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteDeploymentCommandOperation
                                .execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        deploymentId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteDeploymentCommandResponse::new);
    }
}
