package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class EnsureMinMatchesOperationImpl implements EnsureMinMatchesOperation {

    final CreateMatchmakerMatchResourceOperation createMatchmakerMatchResourceOperation;

    @Override
    public void execute(final FetchMatchmakerResult fetchMatchmakerResult,
                        final HandleMatchmakerResult handleMatchmakerResult) {
        fetchMatchmakerResult.deploymentConfig().getMatchmaker().getModes()
                .forEach((mode, config) -> {
                    final var countMatches = fetchMatchmakerResult
                            .matchmakerState().getMatchmakerMatchResources().stream()
                            .filter(matchmakerMatchResource -> matchmakerMatchResource.getMode().equals(mode))
                            .count();
                    final var minMatches = config.getMinMatches();
                    if (countMatches < minMatches) {
                        log.info("Current match count \"{}\" is below the minimum \"{}\", create a new \"{}\" one",
                                countMatches, minMatches, mode);

                        createMatchmakerMatchResourceOperation.execute(fetchMatchmakerResult,
                                handleMatchmakerResult,
                                mode);
                    }
                });
    }
}
