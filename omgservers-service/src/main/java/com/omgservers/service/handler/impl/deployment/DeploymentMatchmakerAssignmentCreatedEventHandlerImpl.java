package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.GetDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.GetDeploymentMatchmakerAssignmentResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeploymentMatchmakerAssignmentCreatedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_MATCHMAKER_ASSIGNMENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (DeploymentMatchmakerAssignmentCreatedEventBodyModel) event.getBody();
        final var deploymentId = body.getDeploymentId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getDeploymentMatchmakerAssignment(deploymentId, id)
                .flatMap(deploymentMatchmakerAssignment -> {
                    log.debug("Created, {}", deploymentMatchmakerAssignment);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<DeploymentMatchmakerAssignmentModel> getDeploymentMatchmakerAssignment(final Long tenantId, final Long id) {
        final var request = new GetDeploymentMatchmakerAssignmentRequest(tenantId, id);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentMatchmakerAssignmentResponse::getDeploymentMatchmakerAssignment);
    }
}
