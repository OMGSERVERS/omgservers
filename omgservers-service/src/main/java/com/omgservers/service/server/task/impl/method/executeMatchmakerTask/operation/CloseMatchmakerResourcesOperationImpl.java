package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerMatchResourceToUpdateStatusDto;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CloseMatchmakerResourcesOperationImpl implements CloseMatchmakerResourcesOperation {

    @Override
    public void execute(final FetchMatchmakerResult fetchMatchmakerResult,
                        final HandleMatchmakerResult handleMatchmakerResult) {
        closeMatchmakerMatchResources(fetchMatchmakerResult, handleMatchmakerResult);
    }

    void closeMatchmakerMatchResources(final FetchMatchmakerResult fetchMatchmakerResult,
                                       final HandleMatchmakerResult handleMatchmakerResult) {
        final var matchmakerId = fetchMatchmakerResult.matchmakerId();

        final var matchmakerMatchResourcesToUpdateStatus = fetchMatchmakerResult.matchmakerState()
                .getMatchmakerMatchResources().stream()
                .filter(matchmakerMatchResource -> matchmakerMatchResource.getStatus()
                        .equals(MatchmakerMatchResourceStatusEnum.CREATED))
                .map(MatchmakerMatchResourceModel::getId)
                .toList();

        if (!matchmakerMatchResourcesToUpdateStatus.isEmpty()) {
            matchmakerMatchResourcesToUpdateStatus.forEach(matchmakerMatchResourceId -> {
                final var dtoToUpdateStatus = new MatchmakerMatchResourceToUpdateStatusDto(
                        matchmakerMatchResourceId,
                        MatchmakerMatchResourceStatusEnum.CLOSED);

                handleMatchmakerResult.matchmakerChangeOfState()
                        .getMatchmakerMatchResourcesToUpdateStatus()
                        .add(dtoToUpdateStatus);
            });

            log.info("\"{}\" match resources from matchmaker \"{}\" marked as closed",
                    matchmakerMatchResourcesToUpdateStatus.size(),
                    matchmakerId);
        }
    }
}
