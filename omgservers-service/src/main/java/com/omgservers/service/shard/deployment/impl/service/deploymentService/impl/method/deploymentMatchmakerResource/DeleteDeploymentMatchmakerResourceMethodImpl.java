package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.DeleteDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.DeleteDeploymentMatchmakerResourceResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.DeleteDeploymentMatchmakerResourceOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDeploymentMatchmakerResourceMethodImpl implements DeleteDeploymentMatchmakerResourceMethod {

    final DeleteDeploymentMatchmakerResourceOperation deleteDeploymentMatchmakerResourceOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteDeploymentMatchmakerResourceResponse> execute(final ShardModel shardModel,
                                                                   final DeleteDeploymentMatchmakerResourceRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteDeploymentMatchmakerResourceOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        deploymentId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteDeploymentMatchmakerResourceResponse::new);
    }
}
