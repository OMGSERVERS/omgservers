package com.omgservers.service.operation.matchmaker;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteMatchmakerMatchAssignmentsOperationImpl implements DeleteMatchmakerMatchAssignmentsOperation {

    final MatchmakerShard matchmakerShard;

    @Override
    public Uni<Void> execute(final Long matchmakerId) {
        return viewMatchmakerMatchAssignments(matchmakerId)
                .flatMap(matchmakerMatchAssignments ->
                        Multi.createFrom().iterable(matchmakerMatchAssignments)
                                .onItem().transformToUniAndConcatenate(matchmakerMatchAssignment -> {
                                    final var matchmakerMatchAssignmentId = matchmakerMatchAssignment.getId();
                                    return deleteMatchmakerMatchAssignment(matchmakerId, matchmakerMatchAssignmentId)
                                            .onFailure()
                                            .recoverWithItem(t -> {
                                                log.error("Failed to delete, id={}/{}, {}:{}",
                                                        matchmakerId,
                                                        matchmakerMatchAssignmentId,
                                                        t.getClass().getSimpleName(),
                                                        t.getMessage());
                                                return Boolean.FALSE;
                                            });
                                })
                                .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<MatchmakerMatchAssignmentModel>> viewMatchmakerMatchAssignments(final Long matchmakerId) {
        final var request = new ViewMatchmakerMatchAssignmentsRequest();
        request.setMatchmakerId(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(ViewMatchmakerMatchAssignmentsResponse::getMatchmakerMatchAssignments);
    }

    Uni<Boolean> deleteMatchmakerMatchAssignment(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerMatchAssignmentRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(DeleteMatchmakerMatchAssignmentResponse::getDeleted);
    }
}
