package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.matchmakerCommand.body.RemoveClientMatchmakerCommandBodyDto;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.GetDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.GetDeploymentMatchmakerAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.SyncMatchmakerCommandRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerCommand.SyncMatchmakerCommandResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeploymentMatchmakerAssignmentDeletedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final MatchmakerShard matchmakerShard;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_MATCHMAKER_ASSIGNMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (DeploymentMatchmakerAssignmentDeletedEventBodyModel) event.getBody();
        final var deploymentId = body.getDeploymentId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getDeploymentMatchmakerAssignment(deploymentId, id)
                .flatMap(deploymentMatchmakerAssignment -> {
                    log.debug("Deleted, {}", deploymentMatchmakerAssignment);

                    final var matchmakerId = deploymentMatchmakerAssignment.getMatchmakerId();
                    final var clientId = deploymentMatchmakerAssignment.getClientId();

                    return createRemoveClientMatchmakerCommand(matchmakerId, clientId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<DeploymentMatchmakerAssignmentModel> getDeploymentMatchmakerAssignment(final Long tenantId, final Long id) {
        final var request = new GetDeploymentMatchmakerAssignmentRequest(tenantId, id);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentMatchmakerAssignmentResponse::getDeploymentMatchmakerAssignment);
    }

    Uni<Boolean> createRemoveClientMatchmakerCommand(final Long matchmakerId,
                                                     final Long clientId,
                                                     final String idempotencyKey) {
        final var commandBody = new RemoveClientMatchmakerCommandBodyDto(clientId);
        final var matchmakerCommand = matchmakerCommandModelFactory
                .create(matchmakerId, commandBody, idempotencyKey);

        final var request = new SyncMatchmakerCommandRequest(matchmakerCommand);
        return matchmakerShard.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
