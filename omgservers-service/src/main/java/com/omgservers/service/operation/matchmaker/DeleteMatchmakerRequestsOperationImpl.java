package com.omgservers.service.operation.matchmaker;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.DeleteMatchmakerRequestResponse;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsResponse;
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
class DeleteMatchmakerRequestsOperationImpl implements DeleteMatchmakerRequestsOperation {

    final MatchmakerShard matchmakerShard;

    @Override
    public Uni<Void> execute(final Long matchmakerId) {
        return viewMatchmakerRequests(matchmakerId)
                .flatMap(matchmakerRequests -> Multi.createFrom().iterable(matchmakerRequests)
                        .onItem().transformToUniAndConcatenate(matchmakerRequest -> {
                            final var matchmakerRequestId = matchmakerRequest.getId();
                            return deleteMatchmakerRequest(matchmakerId, matchmakerRequestId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                matchmakerId,
                                                matchmakerRequestId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<MatchmakerRequestModel>> viewMatchmakerRequests(final Long matchmakerId) {
        final var request = new ViewMatchmakerRequestsRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(ViewMatchmakerRequestsResponse::getMatchmakerRequests);
    }

    Uni<Boolean> deleteMatchmakerRequest(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerRequestRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(DeleteMatchmakerRequestResponse::getDeleted);
    }
}
