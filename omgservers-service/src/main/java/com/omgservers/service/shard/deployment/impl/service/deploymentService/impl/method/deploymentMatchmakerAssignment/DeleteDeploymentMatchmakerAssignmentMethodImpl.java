package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.DeleteDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.DeleteDeploymentMatchmakerAssignmentResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment.DeleteDeploymentMatchmakerAssignmentOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDeploymentMatchmakerAssignmentMethodImpl implements DeleteDeploymentMatchmakerAssignmentMethod {

    final DeleteDeploymentMatchmakerAssignmentOperation deleteDeploymentMatchmakerAssignmentOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteDeploymentMatchmakerAssignmentResponse> execute(final ShardModel shardModel,
                                                                     final DeleteDeploymentMatchmakerAssignmentRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteDeploymentMatchmakerAssignmentOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        deploymentId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteDeploymentMatchmakerAssignmentResponse::new);
    }
}
