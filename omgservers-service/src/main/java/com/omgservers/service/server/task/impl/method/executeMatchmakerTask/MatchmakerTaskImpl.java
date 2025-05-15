package com.omgservers.service.server.task.impl.method.executeMatchmakerTask;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.FetchMatchmakerOperation;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.HandleMatchmakerOperation;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.UpdateMatchmakerOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerTaskImpl implements Task<MatchmakerTaskArguments> {

    final HandleMatchmakerOperation handleMatchmakerOperation;
    final UpdateMatchmakerOperation updateMatchmakerOperation;
    final FetchMatchmakerOperation fetchMatchmakerOperation;

    public Uni<TaskResult> execute(final MatchmakerTaskArguments taskArguments) {
        final var matchmakerId = taskArguments.matchmakerId();

        return fetchMatchmakerOperation.execute(matchmakerId)
                .map(handleMatchmakerOperation::execute)
                .flatMap(handleMatchmakerResult -> {
                    final var matchmakerChangeOfState = handleMatchmakerResult.matchmakerChangeOfState();
                    if (matchmakerChangeOfState.isNotEmpty()) {
                        log.info("Update matchmaker state, matchmakerId={}, {}",
                                matchmakerId, matchmakerChangeOfState);

                        return updateMatchmakerOperation.execute(handleMatchmakerResult)
                                .replaceWith(TaskResult.DONE);
                    } else {
                        return Uni.createFrom().item(TaskResult.NOOP);
                    }
                });
    }
}
