package com.omgservers.service.server.task.impl.method.executeMatchmakerTask;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.FetchMatchmakerOperation;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.HandleMatchmakerOperation;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation.OpenMatchmakerOperation;
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
    final OpenMatchmakerOperation openMatchmakerOperation;

    public Uni<TaskResult> execute(final MatchmakerTaskArguments taskArguments) {
        final var matchmakerId = taskArguments.matchmakerId();
        return fetchMatchmakerOperation.execute(matchmakerId)
                .flatMap(fetchMatchmakerResult -> {
                    final var deleted = fetchMatchmakerResult.matchmakerState().getMatchmaker().getDeleted();
                    if (deleted) {
                        log.warn("Matchmaker \"{}\" deleted, skip task execution", matchmakerId);
                        return Uni.createFrom().item(TaskResult.DONE);
                    } else {
                        final var status = fetchMatchmakerResult.deploymentMatchmakerResource().getStatus();
                        return switch (status) {
                            case PENDING -> handlePendingMatchmaker(fetchMatchmakerResult);
                            case CREATED -> handleCreatedMatchmaker(fetchMatchmakerResult);
                            case CLOSED -> handleClosedMatchmaker(fetchMatchmakerResult);
                        };
                    }
                });
    }

    Uni<TaskResult> handlePendingMatchmaker(final FetchMatchmakerResult fetchMatchmakerResult) {
        return openMatchmakerOperation.execute(fetchMatchmakerResult)
                .replaceWith(TaskResult.DONE);
    }

    Uni<TaskResult> handleCreatedMatchmaker(final FetchMatchmakerResult fetchMatchmakerResult) {
        return handleMatchmaker(fetchMatchmakerResult);
    }

    Uni<TaskResult> handleClosedMatchmaker(final FetchMatchmakerResult fetchMatchmakerResult) {
        return handleMatchmaker(fetchMatchmakerResult);
    }

    Uni<TaskResult> handleMatchmaker(final FetchMatchmakerResult fetchMatchmakerResult) {
        final var matchmakerId = fetchMatchmakerResult.matchmakerId();
        return Uni.createFrom().item(fetchMatchmakerResult)
                .map(handleMatchmakerOperation::execute)
                .flatMap(handleMatchmakerResult -> {
                    final var matchmakerChangeOfState = handleMatchmakerResult.matchmakerChangeOfState();
                    if (matchmakerChangeOfState.isNotEmpty()) {
                        log.info("Update matchmaker state, matchmakerId={}, {}", matchmakerId, matchmakerChangeOfState);
                        return updateMatchmakerOperation.execute(handleMatchmakerResult)
                                .replaceWith(TaskResult.DONE);
                    } else {
                        return Uni.createFrom().item(TaskResult.NOOP);
                    }
                });
    }
}
