package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchOperation.UpsertMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.SyncMatchInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.SyncMatchInternalResponse;
import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncMatchInternalResponse> syncMatch(SyncMatchInternalRequest request) {
        SyncMatchInternalRequest.validate(request);

        return Uni.createFrom().voidItem()
                .flatMap(validatedProject -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shard -> {
                    final var match = request.getMatch();
                    return syncMatch(shard.shard(), match);
                })
                .map(SyncMatchInternalResponse::new);
    }

    Uni<Boolean> syncMatch(final int shard, final MatchModel match) {
        return pgPool.withTransaction(sqlConnection -> upsertMatchOperation
                        .upsertMatch(sqlConnection, shard, match)
                        .call(inserted -> {
                            if (inserted) {
                                final var matchmakerId = match.getMatchmakerId();
                                final var id = match.getId();
                                final var eventBody = new MatchCreatedEventBodyModel(matchmakerId, id);
                                final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                                return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest)
                                        .replaceWithVoid();
                            } else {
                                return Uni.createFrom().voidItem();
                            }
                        })
                        .call(inserted -> {
                            final LogModel syncLog;
                            if (inserted) {
                                syncLog = logModelFactory.create("Match was created, match=" + match);
                            } else {
                                syncLog = logModelFactory.create("Match was updated, match=" + match);
                            }
                            final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                            return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                        }))
                .invoke(voidItem -> matchmakerInMemoryCache.addMatch(match));
    }
}
