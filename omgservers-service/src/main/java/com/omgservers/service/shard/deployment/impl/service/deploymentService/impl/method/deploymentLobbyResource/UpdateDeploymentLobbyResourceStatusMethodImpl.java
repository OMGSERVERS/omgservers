package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyResource;

import com.omgservers.schema.module.deployment.deploymentLobbyResource.UpdateDeploymentLobbyResourceStatusRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.UpdateDeploymentLobbyResourceStatusResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.UpdateDeploymentLobbyResourceStatusOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateDeploymentLobbyResourceStatusMethodImpl implements UpdateDeploymentLobbyResourceStatusMethod {

    final UpdateDeploymentLobbyResourceStatusOperation updateDeploymentLobbyResourceStatusOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateDeploymentLobbyResourceStatusResponse> execute(final UpdateDeploymentLobbyResourceStatusRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();
        final var status = request.getStatus();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                            updateDeploymentLobbyResourceStatusOperation.execute(changeContext,
                                    sqlConnection,
                                    shard,
                                    deploymentId,
                                    id,
                                    status
                            ));
                })
                .map(ChangeContext::getResult)
                .map(UpdateDeploymentLobbyResourceStatusResponse::new);
    }
}
