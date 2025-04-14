package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.operation;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class UpdateMatchmakerOperationImpl implements UpdateMatchmakerOperation {

    final MatchmakerShard matchmakerShard;

    @Override
    public Uni<Void> execute(final HandleMatchmakerResult handleMatchmakerResult) {
        final var matchmakerId = handleMatchmakerResult.matchmakerId();
        final var matchmakerChangeOfState = handleMatchmakerResult.matchmakerChangeOfState();

        return updateMatchmakerState(matchmakerId, matchmakerChangeOfState)
                .replaceWithVoid();
    }

    Uni<Boolean> updateMatchmakerState(final Long matchmakerId,
                                       final MatchmakerChangeOfStateDto matchmakerChangeOfState) {
        final var request = new UpdateMatchmakerStateRequest(matchmakerId, matchmakerChangeOfState);
        return matchmakerShard.getService().execute(request)
                .map(UpdateMatchmakerStateResponse::getUpdated);
    }
}
