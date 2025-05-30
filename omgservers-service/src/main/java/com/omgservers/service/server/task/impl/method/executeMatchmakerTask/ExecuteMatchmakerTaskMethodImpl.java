package com.omgservers.service.server.task.impl.method.executeMatchmakerTask;

import com.omgservers.service.operation.task.ExecuteTaskOperation;
import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteMatchmakerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteMatchmakerTaskMethodImpl implements ExecuteMatchmakerTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final MatchmakerTaskImpl matchmakerTask;

    @Override
    public Uni<ExecuteMatchmakerTaskResponse> execute(final ExecuteMatchmakerTaskRequest request) {
        log.debug("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var taskArguments = new MatchmakerTaskArguments(matchmakerId);
        return executeTaskOperation.executeFailSafe(matchmakerTask, taskArguments)
                .map(ExecuteMatchmakerTaskResponse::new);
    }
}
