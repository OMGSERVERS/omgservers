package com.omgservers.service.operation.deployment;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.DeleteDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.DeleteDeploymentMatchmakerResourceResponse;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.ViewDeploymentMatchmakerResourcesRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.ViewDeploymentMatchmakerResourcesResponse;
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
class DeleteDeploymentMatchmakerResourcesOperationImpl implements DeleteDeploymentMatchmakerResourcesOperation {

    final DeploymentShard deploymentShard;

    @Override
    public Uni<Void> execute(final Long deploymentId) {
        return viewDeploymentMatchmakerResources(deploymentId)
                .flatMap(deploymentMatchmakerResources -> Multi.createFrom().iterable(deploymentMatchmakerResources)
                        .onItem().transformToUniAndConcatenate(deploymentMatchmakerResource -> {
                            final var deploymentMatchmakerResourceId = deploymentMatchmakerResource.getId();
                            return deleteDeploymentMatchmakerResource(deploymentId, deploymentMatchmakerResourceId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                deploymentId,
                                                deploymentMatchmakerResourceId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<DeploymentMatchmakerResourceModel>> viewDeploymentMatchmakerResources(final Long deploymentId) {
        final var request = new ViewDeploymentMatchmakerResourcesRequest();
        request.setDeploymentId(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(ViewDeploymentMatchmakerResourcesResponse::getDeploymentMatchmakerResources);
    }

    Uni<Boolean> deleteDeploymentMatchmakerResource(final Long deploymentId, final Long id) {
        final var request = new DeleteDeploymentMatchmakerResourceRequest(deploymentId, id);
        return deploymentShard.getService().execute(request)
                .map(DeleteDeploymentMatchmakerResourceResponse::getDeleted);
    }
}
