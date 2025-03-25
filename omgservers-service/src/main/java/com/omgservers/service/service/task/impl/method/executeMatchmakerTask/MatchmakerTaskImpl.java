package com.omgservers.service.service.task.impl.method.executeMatchmakerTask;

import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.FetchMatchmakerOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.HandleMatchmakerOperation;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.UpdateMatchmakerOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerTaskImpl {

    final HandleMatchmakerOperation handleMatchmakerOperation;
    final UpdateMatchmakerOperation updateMatchmakerOperation;
    final FetchMatchmakerOperation fetchMatchmakerOperation;

    public Uni<Boolean> execute(final Long matchmakerId) {
        return fetchMatchmakerOperation.execute(matchmakerId)
                .map(handleMatchmakerOperation::execute)
                .flatMap(updateMatchmakerOperation::execute)
                .replaceWith(Boolean.TRUE);
    }
}
