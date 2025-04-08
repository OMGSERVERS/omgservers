package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.DeleteDeploymentLobbyResourceRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.DeleteDeploymentLobbyResourceResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.DeleteDeploymentLobbyResourceOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDeploymentLobbyResourceMethodImpl implements DeleteDeploymentLobbyResourceMethod {

    final DeleteDeploymentLobbyResourceOperation deleteDeploymentLobbyResourceOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteDeploymentLobbyResourceResponse> execute(final ShardModel shardModel,
                                                              final DeleteDeploymentLobbyResourceRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteDeploymentLobbyResourceOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        deploymentId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteDeploymentLobbyResourceResponse::new);
    }
}
