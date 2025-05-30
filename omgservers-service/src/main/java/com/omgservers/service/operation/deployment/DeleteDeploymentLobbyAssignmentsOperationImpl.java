package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentResponse;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.ViewDeploymentLobbyAssignmentsRequest;
import com.omgservers.schema.shard.deployment.deploymentLobbyAssignment.ViewDeploymentLobbyAssignmentsResponse;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteDeploymentLobbyAssignmentsOperationImpl implements DeleteDeploymentLobbyAssignmentsOperation {

    final DeploymentShard deploymentShard;

    @Override
    public Uni<Void> execute(final Long deploymentId) {
        return viewDeploymentLobbyAssignments(deploymentId)
                .flatMap(deploymentLobbyAssignments -> Multi.createFrom().iterable(deploymentLobbyAssignments)
                        .onItem().transformToUniAndConcatenate(deploymentLobbyAssignment -> {
                            final var deploymentLobbyAssignmentId = deploymentLobbyAssignment.getId();
                            return deleteDeploymentLobbyAssignment(deploymentId, deploymentLobbyAssignmentId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                deploymentId,
                                                deploymentLobbyAssignmentId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<DeploymentLobbyAssignmentModel>> viewDeploymentLobbyAssignments(final Long deploymentId) {
        final var request = new ViewDeploymentLobbyAssignmentsRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(ViewDeploymentLobbyAssignmentsResponse::getDeploymentLobbyAssignments);
    }

    Uni<Boolean> deleteDeploymentLobbyAssignment(final Long deploymentId, final Long id) {
        final var request = new DeleteDeploymentLobbyAssignmentRequest(deploymentId, id);
        return deploymentShard.getService().execute(request)
                .map(DeleteDeploymentLobbyAssignmentResponse::getDeleted);
    }
}
