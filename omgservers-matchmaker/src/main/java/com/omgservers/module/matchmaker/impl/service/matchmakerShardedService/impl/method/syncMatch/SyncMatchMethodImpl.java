package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatch;

import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardResponse;
import com.omgservers.dto.matchmaker.SyncMatchShardedRequest;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.event.body.MatchUpdatedEventBodyModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.factory.EventModelFactory;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.MatchmakerInMemoryCache;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchMethodImpl implements SyncMatchMethod {

    final InternalModule internalModule;

    final UpsertMatchOperation upsertMatchOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;
    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncMatchShardResponse> syncMatch(SyncMatchShardedRequest request) {
        SyncMatchShardedRequest.validate(request);

        final var match = request.getMatch();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertMatchOperation
                                .upsertMatch(sqlConnection, shardModel.shard(), match),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Match was created, match=" + match);
                            } else {
                                return logModelFactory.create("Match was updated, match=" + match);
                            }
                        },
                        inserted -> {
                            if (inserted) {
                                return new MatchCreatedEventBodyModel(match.getMatchmakerId(), match.getId());
                            } else {
                                return new MatchUpdatedEventBodyModel(match.getMatchmakerId(), match.getId());
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .invoke(inserted -> matchmakerInMemoryCache.addMatch(match))
                .map(SyncMatchShardResponse::new);
    }
}
