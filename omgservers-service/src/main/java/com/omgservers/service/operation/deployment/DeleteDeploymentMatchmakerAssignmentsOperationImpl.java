package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.DeleteDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.DeleteDeploymentMatchmakerAssignmentResponse;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsResponse;
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
class DeleteDeploymentMatchmakerAssignmentsOperationImpl implements DeleteDeploymentMatchmakerAssignmentsOperation {

    final DeploymentShard deploymentShard;

    @Override
    public Uni<Void> execute(final Long deploymentId) {
        return viewDeploymentMatchmakerAssignments(deploymentId)
                .flatMap(deploymentMatchmakerAssignments -> Multi.createFrom().iterable(deploymentMatchmakerAssignments)
                        .onItem().transformToUniAndConcatenate(deploymentMatchmakerAssignment -> {
                            final var deploymentMatchmakerAssignmentId = deploymentMatchmakerAssignment.getId();
                            return deleteDeploymentMatchmakerAssignment(deploymentId, deploymentMatchmakerAssignmentId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                deploymentId,
                                                deploymentMatchmakerAssignmentId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<DeploymentMatchmakerAssignmentModel>> viewDeploymentMatchmakerAssignments(final Long deploymentId) {
        final var request = new ViewDeploymentMatchmakerAssignmentsRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(ViewDeploymentMatchmakerAssignmentsResponse::getDeploymentMatchmakerAssignments);
    }

    Uni<Boolean> deleteDeploymentMatchmakerAssignment(final Long deploymentId, final Long id) {
        final var request = new DeleteDeploymentMatchmakerAssignmentRequest(deploymentId, id);
        return deploymentShard.getService().execute(request)
                .map(DeleteDeploymentMatchmakerAssignmentResponse::getDeleted);
    }
}
