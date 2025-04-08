package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentLobbyAsignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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

    @Override
    public Uni<DeleteDeploymentLobbyAssignmentResponse> execute(final ShardModel shardModel,
                                                                final DeleteDeploymentLobbyAssignmentRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteDeploymentLobbyAssignmentOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        deploymentId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteDeploymentLobbyAssignmentResponse::new);
    }
}
