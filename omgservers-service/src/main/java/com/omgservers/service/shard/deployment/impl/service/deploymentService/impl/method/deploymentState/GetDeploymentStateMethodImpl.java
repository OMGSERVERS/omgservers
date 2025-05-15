package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentState;

import com.omgservers.schema.model.deploymentState.DeploymentStateDto;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateRequest;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateResponse;
import com.omgservers.service.shard.deployment.impl.operation.deployment.SelectDeploymentOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand.SelectActiveDeploymentCommandsByDeploymentIdOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment.SelectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.SelectActiveDeploymentLobbyResourcesByDeploymentIdOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment.SelectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.SelectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentRequest.SelectActiveDeploymentRequestsByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDeploymentStateMethodImpl implements GetDeploymentStateMethod {

    final SelectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation
            selectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation;
    final SelectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation
            selectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation;
    final SelectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation
            selectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation;
    final SelectActiveDeploymentLobbyResourcesByDeploymentIdOperation
            selectActiveDeploymentLobbyResourcesByDeploymentIdOperation;
    final SelectActiveDeploymentCommandsByDeploymentIdOperation
            selectActiveDeploymentCommandsByDeploymentIdOperation;
    final SelectActiveDeploymentRequestsByDeploymentIdOperation
            selectActiveDeploymentRequestsByDeploymentIdOperation;

    final SelectDeploymentOperation selectDeploymentOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentStateResponse> execute(final ShardModel shardModel,
                                                   final GetDeploymentStateRequest request) {
        log.debug("{}", request);

        final var slot = shardModel.slot();
        final var deploymentId = request.getDeploymentId();
        final var deploymentState = new DeploymentStateDto();
        return pgPool.withTransaction(sqlConnection ->
                        selectDeploymentOperation.execute(sqlConnection, slot, deploymentId)
                                .invoke(deploymentState::setDeployment)
                                .flatMap(deployment ->
                                        selectActiveDeploymentCommandsByDeploymentIdOperation
                                                .execute(sqlConnection, slot, deploymentId)
                                                .invoke(deploymentState::setDeploymentCommands))
                                .flatMap(deployment ->
                                        selectActiveDeploymentRequestsByDeploymentIdOperation
                                                .execute(sqlConnection, slot, deploymentId)
                                                .invoke(deploymentState::setDeploymentRequests))
                                .flatMap(deployment ->
                                        selectActiveDeploymentLobbyResourcesByDeploymentIdOperation
                                                .execute(sqlConnection, slot, deploymentId)
                                                .invoke(deploymentState::setDeploymentLobbyResources))
                                .flatMap(deployment ->
                                        selectActiveDeploymentLobbyResourcesByDeploymentIdOperation
                                                .execute(sqlConnection, slot, deploymentId)
                                                .invoke(deploymentState::setDeploymentLobbyResources))
                                .flatMap(deployment ->
                                        selectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation
                                                .execute(sqlConnection, slot, deploymentId)
                                                .invoke(deploymentState::setDeploymentLobbyAssignments))
                                .flatMap(deployment ->
                                        selectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation
                                                .execute(sqlConnection, slot, deploymentId)
                                                .invoke(deploymentState::setDeploymentMatchmakerResources))
                                .flatMap(deployment ->
                                        selectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation
                                                .execute(sqlConnection, slot, deploymentId)
                                                .invoke(deploymentState::setDeploymentMatchmakerAssignments))
                )
                .replaceWith(deploymentState)
                .map(GetDeploymentStateResponse::new);
    }
}
