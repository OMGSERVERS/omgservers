package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.task.TaskQualifierEnum;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.task.CreateTaskOperation;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeploymentCreatedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;

    final CreateTaskOperation createTaskOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (DeploymentCreatedEventBodyModel) event.getBody();
        final var deploymentId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getDeployment(deploymentId)
                .flatMap(deployment -> {
                    log.debug("Created, {}", deployment);

                    return createTaskOperation.execute(TaskQualifierEnum.DEPLOYMENT,
                            deploymentId,
                            idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<DeploymentModel> getDeployment(final Long id) {
        final var request = new GetDeploymentRequest(id);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentResponse::getDeployment);
    }
}
