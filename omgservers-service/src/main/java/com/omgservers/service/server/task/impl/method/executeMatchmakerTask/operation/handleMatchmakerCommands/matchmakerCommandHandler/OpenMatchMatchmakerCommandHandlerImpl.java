package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.matchmakerCommandHandler;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerMatchResourceToUpdateStatusDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.OpenMatchMatchmakerCommandBodyDto;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.MatchmakerCommandHandler;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class OpenMatchMatchmakerCommandHandlerImpl implements MatchmakerCommandHandler {

    @Override
    public MatchmakerCommandQualifierEnum getQualifier() {
        return MatchmakerCommandQualifierEnum.OPEN_MATCH;
    }

    @Override
    public boolean handle(final FetchMatchmakerResult fetchMatchmakerResult,
                          final HandleMatchmakerResult handleMatchmakerResult,
                          final MatchmakerCommandModel matchmakerCommand) {
        log.trace("Handle command, {}", matchmakerCommand);

        final var matchmakerState = fetchMatchmakerResult.matchmakerState();
        final var body = (OpenMatchMatchmakerCommandBodyDto) matchmakerCommand.getBody();
        final var matchId = body.getMatchId();

        final var matchmakerId = fetchMatchmakerResult.matchmakerId();

        matchmakerState.getMatchmakerMatchResources().stream()
                .filter(matchmakerMatchResource -> matchmakerMatchResource.getMatchId().equals(matchId))
                .filter(matchmakerMatchResource -> matchmakerMatchResource.getStatus()
                        .equals(MatchmakerMatchResourceStatusEnum.PENDING))
                .map(MatchmakerMatchResourceModel::getId)
                .forEach(matchmakerMatchResourceId -> {
                    final var dtoToUpdateStatus = new MatchmakerMatchResourceToUpdateStatusDto(
                            matchmakerMatchResourceId,
                            MatchmakerMatchResourceStatusEnum.CREATED);

                    handleMatchmakerResult.matchmakerChangeOfState()
                            .getMatchmakerMatchResourcesToUpdateStatus()
                            .add(dtoToUpdateStatus);

                    log.info("Match resource \"{}\" from matchmaker \"{}\" marked as created, matchId={}",
                            matchmakerMatchResourceId,
                            matchmakerId,
                            matchId);
                });

        return true;
    }
}
