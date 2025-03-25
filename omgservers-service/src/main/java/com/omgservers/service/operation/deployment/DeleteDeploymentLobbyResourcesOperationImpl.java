package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.DeleteDeploymentLobbyResourceRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.DeleteDeploymentLobbyResourceResponse;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.ViewDeploymentLobbyResourcesRequest;
import com.omgservers.schema.module.deployment.deploymentLobbyResource.ViewDeploymentLobbyResourcesResponse;
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
class DeleteDeploymentLobbyResourcesOperationImpl implements DeleteDeploymentLobbyResourcesOperation {

    final DeploymentShard deploymentShard;

    @Override
    public Uni<Void> execute(final Long deploymentId) {
        return viewDeploymentLobbyResources(deploymentId)
                .flatMap(deploymentLobbyResources -> Multi.createFrom().iterable(deploymentLobbyResources)
                        .onItem().transformToUniAndConcatenate(deploymentLobbyResource -> {
                            final var deploymentLobbyResourceId = deploymentLobbyResource.getId();
                            return deleteDeploymentLobbyResource(deploymentId, deploymentLobbyResourceId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                deploymentId,
                                                deploymentLobbyResourceId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<DeploymentLobbyResourceModel>> viewDeploymentLobbyResources(final Long deploymentId) {
        final var request = new ViewDeploymentLobbyResourcesRequest();
        request.setDeploymentId(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(ViewDeploymentLobbyResourcesResponse::getDeploymentLobbyResources);
    }

    Uni<Boolean> deleteDeploymentLobbyResource(final Long deploymentId, final Long id) {
        final var request = new DeleteDeploymentLobbyResourceRequest(deploymentId, id);
        return deploymentShard.getService().execute(request)
                .map(DeleteDeploymentLobbyResourceResponse::getDeleted);
    }
}
