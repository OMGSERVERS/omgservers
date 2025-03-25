package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDeploymentLobbyAssignmentMethodImpl implements DeleteDeploymentLobbyAssignmentMethod {

    final DeleteDeploymentLobbyAssignmentOperation deleteDeploymentLobbyAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteDeploymentLobbyAssignmentResponse> execute(final DeleteDeploymentLobbyAssignmentRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteDeploymentLobbyAssignmentOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                deploymentId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteDeploymentLobbyAssignmentResponse::new);
    }
}
