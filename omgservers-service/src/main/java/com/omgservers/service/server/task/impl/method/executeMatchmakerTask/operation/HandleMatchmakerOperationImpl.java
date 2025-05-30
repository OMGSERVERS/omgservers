package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands.HandleMatchmakerCommandsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleMatchmakerOperationImpl implements HandleMatchmakerOperation {

    final HandleMatchmakerCommandsOperation handleMatchmakerCommandsOperation;
    final HandleMatchmakerRequestsOperation handleMatchmakerRequestsOperation;
    final DeleteClosedMatchesOperation deleteClosedMatchesOperation;
    final EnsureMinMatchesOperation ensureMinMatchesOperation;

    @Override
    public HandleMatchmakerResult execute(final FetchMatchmakerResult fetchMatchmakerResult) {
        final var matchmakerId = fetchMatchmakerResult.matchmakerId();
        final var handleMatchmakerResult = new HandleMatchmakerResult(matchmakerId,
                new MatchmakerChangeOfStateDto());

        deleteClosedMatchesOperation.execute(fetchMatchmakerResult, handleMatchmakerResult);

        ensureMinMatchesOperation.execute(fetchMatchmakerResult, handleMatchmakerResult);

        handleMatchmakerCommandsOperation.execute(fetchMatchmakerResult, handleMatchmakerResult);
        handleMatchmakerRequestsOperation.execute(fetchMatchmakerResult, handleMatchmakerResult);

        return handleMatchmakerResult;
    }
}
