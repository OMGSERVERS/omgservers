package com.omgservers.service.operation.matchmaker;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.DeleteMatchmakerMatchResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesResponse;
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
class DeleteMatchmakerMatchesOperationImpl implements DeleteMatchmakerMatchesOperation {

    final MatchmakerShard matchmakerShard;

    @Override
    public Uni<Void> execute(final Long matchmakerId) {
        return viewMatchmakerMatches(matchmakerId)
                .flatMap(matchmakerMatches -> Multi.createFrom().iterable(matchmakerMatches)
                        .onItem().transformToUniAndConcatenate(matchmakerMatch -> {
                            final var matchmakerMatchId = matchmakerMatch.getId();
                            return deleteMatchmakerMatches(matchmakerId, matchmakerMatchId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                matchmakerId,
                                                matchmakerMatchId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<MatchmakerMatchResourceModel>> viewMatchmakerMatches(final Long matchmakerId) {
        final var request = new ViewMatchmakerMatchResourcesRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(ViewMatchmakerMatchResourcesResponse::getMatchmakerMatchResources);
    }

    Uni<Boolean> deleteMatchmakerMatches(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerMatchResourceRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(DeleteMatchmakerMatchResourceResponse::getDeleted);
    }
}
