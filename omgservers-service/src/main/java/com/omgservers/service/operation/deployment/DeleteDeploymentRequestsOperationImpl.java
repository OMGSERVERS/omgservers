package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.schema.shard.deployment.deploymentRequest.DeleteDeploymentRequestRequest;
import com.omgservers.schema.shard.deployment.deploymentRequest.DeleteDeploymentRequestResponse;
import com.omgservers.schema.shard.deployment.deploymentRequest.ViewDeploymentRequestsRequest;
import com.omgservers.schema.shard.deployment.deploymentRequest.ViewDeploymentRequestsResponse;
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
class DeleteDeploymentRequestsOperationImpl implements DeleteDeploymentRequestsOperation {

    final DeploymentShard deploymentShard;

    @Override
    public Uni<Void> execute(final Long deploymentId) {
        return viewDeploymentRequests(deploymentId)
                .flatMap(deploymentRequests -> Multi.createFrom().iterable(deploymentRequests)
                        .onItem().transformToUniAndConcatenate(deploymentRequest -> {
                            final var deploymentRequestId = deploymentRequest.getId();
                            return deleteDeploymentRequest(deploymentId, deploymentRequestId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                deploymentId,
                                                deploymentRequestId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<DeploymentRequestModel>> viewDeploymentRequests(final Long deploymentId) {
        final var request = new ViewDeploymentRequestsRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(ViewDeploymentRequestsResponse::getDeploymentRequests);
    }

    Uni<Boolean> deleteDeploymentRequest(final Long deploymentId, final Long id) {
        final var request = new DeleteDeploymentRequestRequest(deploymentId, id);
        return deploymentShard.getService().execute(request)
                .map(DeleteDeploymentRequestResponse::getDeleted);
    }
}
