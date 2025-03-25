package com.omgservers.service.service.task.impl.method.executeMatchmakerTask;

import com.omgservers.service.operation.job.ExecuteTaskOperation;
import com.omgservers.service.service.task.dto.ExecuteMatchmakerTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteMatchmakerTaskResponse;
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
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();

        return executeTaskOperation.execute(matchmakerTask.execute(matchmakerId))
                .map(ExecuteMatchmakerTaskResponse::new);
    }
}
