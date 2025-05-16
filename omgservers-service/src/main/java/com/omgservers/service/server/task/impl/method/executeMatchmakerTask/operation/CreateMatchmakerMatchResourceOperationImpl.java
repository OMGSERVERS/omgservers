package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchResourceModelFactory;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreateMatchmakerMatchResourceOperationImpl implements CreateMatchmakerMatchResourceOperation {

    final MatchmakerMatchResourceModelFactory matchmakerMatchResourceModelFactory;

    @Override
    public Optional<MatchmakerMatchResourceModel> execute(final FetchMatchmakerResult fetchMatchmakerResult,
                                                          final HandleMatchmakerResult handleMatchmakerResult,
                                                          final String modeName) {
        if (handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerMatchResourcesToSync().isEmpty()) {

            final var currentMatches = fetchMatchmakerResult.matchmakerState().getMatchmakerMatchResources().size();
            final var maxMatches = fetchMatchmakerResult.deploymentConfig().getMatchmaker().getMaxMatches();
            if (currentMatches >= maxMatches) {
                log.info("Reached maximum number of matches \"{}\", skip operation", maxMatches);
                return Optional.empty();
            }

            final var matchmakerId = fetchMatchmakerResult.matchmakerId();

            final var matchmakerMatchResource = matchmakerMatchResourceModelFactory.create(matchmakerId, modeName);
            handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerMatchResourcesToSync()
                    .add(matchmakerMatchResource);

            return Optional.of(matchmakerMatchResource);
        }

        return Optional.empty();
    }
}
