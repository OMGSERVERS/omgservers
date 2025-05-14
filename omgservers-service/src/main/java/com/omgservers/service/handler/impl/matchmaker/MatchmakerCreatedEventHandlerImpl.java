package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.deploymentCommand.body.OpenMatchmakerDeploymentCommandBodyDto;
import com.omgservers.schema.model.task.TaskQualifierEnum;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.shard.deployment.deploymentCommand.SyncDeploymentCommandRequest;
import com.omgservers.schema.shard.deployment.deploymentCommand.SyncDeploymentCommandResponse;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerCreatedEventBodyModel;
import com.omgservers.service.factory.deployment.DeploymentCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.task.CreateTaskOperation;
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
public class MatchmakerCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final DeploymentShard deploymentShard;

    final CreateTaskOperation createTaskOperation;

    final DeploymentCommandModelFactory deploymentCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    log.debug("Created, {}", matchmaker);

                    final var deploymentId = matchmaker.getDeploymentId();

                    return createOpenMatchmakerDeploymentCommand(deploymentId, matchmakerId, idempotencyKey)
                            .flatMap(created -> createTaskOperation.execute(TaskQualifierEnum.MATCHMAKER,
                                    matchmakerId,
                                    idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<Boolean> createOpenMatchmakerDeploymentCommand(final Long deploymentId,
                                                       final Long matchmakerId,
                                                       final String idempotencyKey) {
        final var commandBody = new OpenMatchmakerDeploymentCommandBodyDto(matchmakerId);
        final var deploymentCommand = deploymentCommandModelFactory.create(deploymentId,
                commandBody,
                idempotencyKey);

        final var request = new SyncDeploymentCommandRequest(deploymentCommand);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentCommandResponse::getCreated);
    }
}
