package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.deployment.DeleteDeploymentCommandsOperation;
import com.omgservers.service.operation.deployment.DeleteDeploymentLobbyResourcesOperation;
import com.omgservers.service.operation.deployment.DeleteDeploymentMatchmakerResourcesOperation;
import com.omgservers.service.operation.deployment.DeleteDeploymentRequestsOperation;
import com.omgservers.service.operation.job.FindAndDeleteJobOperation;
import com.omgservers.service.operation.tenant.FindAndDeleteTenantDeploymentRefOperation;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeploymentDeletedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;

    final DeleteDeploymentMatchmakerResourcesOperation deleteDeploymentMatchmakerResourcesOperation;
    final FindAndDeleteTenantDeploymentRefOperation findAndDeleteTenantDeploymentRefOperation;
    final DeleteDeploymentLobbyResourcesOperation deleteDeploymentLobbyResourcesOperation;
    final DeleteDeploymentCommandsOperation deleteDeploymentCommandsOperation;
    final DeleteDeploymentRequestsOperation deleteDeploymentRequestsOperation;
    final FindAndDeleteJobOperation findAndDeleteJobOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (DeploymentDeletedEventBodyModel) event.getBody();
        final var deploymentId = body.getId();

        return getDeployment(deploymentId)
                .flatMap(deployment -> {
                    log.debug("Deleted, {}", deployment);

                    return deleteDeploymentCommandsOperation.execute(deploymentId)
                            .flatMap(voidItem -> deleteDeploymentRequestsOperation.execute(deploymentId))
                            .flatMap(voidItem -> deleteDeploymentLobbyResourcesOperation.execute(deploymentId))
                            .flatMap(voidItem -> deleteDeploymentMatchmakerResourcesOperation.execute(deploymentId))
                            .flatMap(voidItem -> {
                                final var tenantId = deployment.getTenantId();
                                return findAndDeleteTenantDeploymentRefOperation.execute(tenantId, deploymentId);
                            })
                            .flatMap(voidItem -> findAndDeleteJobOperation.execute(deploymentId, deploymentId));
                })
                .replaceWithVoid();
    }

    Uni<DeploymentModel> getDeployment(final Long deploymentId) {
        final var request = new GetDeploymentRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentResponse::getDeployment);
    }
}
