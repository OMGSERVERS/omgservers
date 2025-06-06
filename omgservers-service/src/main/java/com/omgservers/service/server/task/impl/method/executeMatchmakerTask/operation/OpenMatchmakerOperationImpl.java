package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.operation.deployment.CreateOpenMatchmakerDeploymentCommandOperation;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class OpenMatchmakerOperationImpl implements OpenMatchmakerOperation {

    final CreateOpenMatchmakerDeploymentCommandOperation createOpenMatchmakerDeploymentCommandOperation;

    @Override
    public Uni<Void> execute(final FetchMatchmakerResult fetchMatchmakerResult) {
        final var deploymentId = fetchMatchmakerResult.matchmakerState().getMatchmaker().getDeploymentId();
        final var matchmakerId = fetchMatchmakerResult.matchmakerId();
        return createOpenMatchmakerDeploymentCommandOperation.execute(deploymentId, matchmakerId)
                .invoke(created -> {
                    if (created) {
                        log.info("Created open matchmaker \"{}\" command in deployment \"{}\"",
                                matchmakerId, deploymentId);
                    }
                })
                .replaceWithVoid();
    }
}
