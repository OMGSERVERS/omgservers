package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteTenantDeploymentResourcesOperationImpl implements DeleteTenantDeploymentResourcesOperation {

    final DeploymentShard deploymentShard;
    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final List<TenantDeploymentResourceModel> tenantDeploymentResources) {
        return Multi.createFrom().iterable(tenantDeploymentResources)
                .onItem().transformToUniAndConcatenate(tenantDeploymentResource -> {
                            final var deploymentId = tenantDeploymentResource.getDeploymentId();
                            return viewDeploymentMatchmakerAssignment(deploymentId)
                                    .flatMap(deploymentMatchmakerAssignments -> {
                                        if (deploymentMatchmakerAssignments.isEmpty()) {
                                            final var tenantId = tenantDeploymentResource.getTenantId();
                                            final var id = tenantDeploymentResource.getId();
                                            return deleteTenantDeploymentResource(tenantId, id)
                                                    .invoke(deleted -> {
                                                        if (deleted) {
                                                            log.info("Deleted tenant deployment resource \"{}\" of " +
                                                                            "tenant \"{}\", deploymentId=\"{}\"",
                                                                    id, tenantId, deploymentId);
                                                        }
                                                    });
                                        } else {
                                            return Uni.createFrom().voidItem();
                                        }
                                    })
                                    .onFailure(ServerSideClientException.class)
                                    .recoverWithItem(t -> {
                                        log.warn("Failed to delete, id={}/{}, {}:{}",
                                                tenantDeploymentResource.getTenantId(),
                                                tenantDeploymentResource.getId(),
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return null;
                                    });
                        }
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<List<DeploymentMatchmakerAssignmentModel>> viewDeploymentMatchmakerAssignment(final Long deploymentId) {
        final var request = new ViewDeploymentMatchmakerAssignmentsRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(ViewDeploymentMatchmakerAssignmentsResponse::getDeploymentMatchmakerAssignments);
    }

    Uni<Boolean> deleteTenantDeploymentResource(final Long tenantId, final Long id) {
        final var request = new DeleteTenantDeploymentResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantDeploymentResourceResponse::getDeleted);
    }
}
