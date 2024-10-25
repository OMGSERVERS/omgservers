package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand.HandleMatchmakerCommandOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleMatchmakerCommandsOperationImpl implements HandleMatchmakerCommandsOperation {

    final HandleMatchmakerCommandOperation handleMatchmakerCommandOperation;

    @Override
    public void execute(final MatchmakerStateDto matchmakerState,
                        final MatchmakerChangeOfStateDto matchmakerChangeOfState) {
        matchmakerState.getMatchmakerCommands().forEach(matchmakerCommand ->
                handleMatchmakerCommandOperation.execute(matchmakerState, matchmakerChangeOfState, matchmakerCommand));
    }
}
