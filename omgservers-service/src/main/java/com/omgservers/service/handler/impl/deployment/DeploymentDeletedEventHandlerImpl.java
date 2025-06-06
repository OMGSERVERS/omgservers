package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deploymentState.DeploymentStateDto;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateRequest;
import com.omgservers.schema.shard.deployment.deploymentState.GetDeploymentStateResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.deployment.DeleteDeploymentCommandsOperation;
import com.omgservers.service.operation.deployment.DeleteDeploymentLobbyAssignmentsOperation;
import com.omgservers.service.operation.deployment.DeleteDeploymentLobbyResourcesOperation;
import com.omgservers.service.operation.deployment.DeleteDeploymentMatchmakerResourcesOperation;
import com.omgservers.service.operation.deployment.DeleteDeploymentRequestsOperation;
import com.omgservers.service.operation.deployment.DeleteDeploymentMatchmakerAssignmentsOperation;
import com.omgservers.service.operation.task.DeleteTaskOperation;
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

    final FindAndDeleteTenantDeploymentRefOperation findAndDeleteTenantDeploymentRefOperation;
    final DeleteTaskOperation deleteTaskOperation;

    final DeleteDeploymentMatchmakerAssignmentsOperation deleteDeploymentMatchmakerAssignmentsOperation;
    final DeleteDeploymentMatchmakerResourcesOperation deleteDeploymentMatchmakerResourcesOperation;
    final DeleteDeploymentLobbyAssignmentsOperation deleteDeploymentLobbyAssignmentsOperation;
    final DeleteDeploymentLobbyResourcesOperation deleteDeploymentLobbyResourcesOperation;
    final DeleteDeploymentCommandsOperation deleteDeploymentCommandsOperation;
    final DeleteDeploymentRequestsOperation deleteDeploymentRequestsOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (DeploymentDeletedEventBodyModel) event.getBody();
        final var deploymentId = body.getId();

        return getDeploymentState(deploymentId)
                .flatMap(deploymentState -> {
                    final var deployment = deploymentState.getDeployment();
                    log.debug("Deleted, {}", deployment);

                    final var tenantId = deployment.getTenantId();
                    return findAndDeleteTenantDeploymentRefOperation.execute(tenantId, deploymentId)
                            .flatMap(voidItem -> deleteTaskOperation.execute(deploymentId, deploymentId))
                            .flatMap(deleted -> {
                                final var deploymentCommands = deploymentState.getDeploymentCommands();
                                final var deploymentRequests = deploymentState.getDeploymentRequests();
                                final var deploymentLobbyResources =
                                        deploymentState.getDeploymentLobbyResources();
                                final var deploymentLobbyAssignments =
                                        deploymentState.getDeploymentLobbyAssignments();
                                final var deploymentMatchmakerResources =
                                        deploymentState.getDeploymentMatchmakerResources();
                                final var deploymentMatchmakerAssignments =
                                        deploymentState.getDeploymentMatchmakerAssignments();

                                if (!deploymentCommands.isEmpty() ||
                                        !deploymentRequests.isEmpty() ||
                                        !deploymentLobbyResources.isEmpty() ||
                                        !deploymentLobbyAssignments.isEmpty() ||
                                        !deploymentMatchmakerResources.isEmpty() ||
                                        !deploymentMatchmakerAssignments.isEmpty()) {
                                    log.error("Deployment \"{}\" deleted, but some data remains, " +
                                                    "commands={}, " +
                                                    "requests={}, " +
                                                    "lobbyResources={}, " +
                                                    "lobbyAssignments={}, " +
                                                    "matchmakerResources={}, " +
                                                    "matchmakerAssignments={}",
                                            deploymentId,
                                            deploymentCommands.size(),
                                            deploymentRequests.size(),
                                            deploymentLobbyResources.size(),
                                            deploymentLobbyAssignments.size(),
                                            deploymentMatchmakerResources.size(),
                                            deploymentMatchmakerAssignments.size());

                                    return Uni.createFrom().voidItem()
                                            .flatMap(voidItem -> deleteDeploymentCommandsOperation
                                                    .execute(deploymentId))
                                            .flatMap(voidItem -> deleteDeploymentRequestsOperation
                                                    .execute(deploymentId))
                                            .flatMap(voidItem -> deleteDeploymentLobbyResourcesOperation
                                                    .execute(deploymentId))
                                            .flatMap(voidItem -> deleteDeploymentLobbyAssignmentsOperation
                                                    .execute(deploymentId))
                                            .flatMap(voidItem -> deleteDeploymentMatchmakerResourcesOperation
                                                    .execute(deploymentId))
                                            .flatMap(voidItem -> deleteDeploymentMatchmakerAssignmentsOperation
                                                    .execute(deploymentId));
                                } else {
                                    return Uni.createFrom().voidItem();
                                }
                            });
                })
                .replaceWithVoid();
    }

    Uni<DeploymentStateDto> getDeploymentState(final Long deploymentId) {
        final var request = new GetDeploymentStateRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentStateResponse::getDeploymentState);
    }
}
