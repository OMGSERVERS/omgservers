package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.createMatchMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchOperation.InsertMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.CreateMatchInternalRequest;
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
class CreateMatchMethodImpl implements CreateMatchMethod {

    final InternalModule internalModule;

    final InsertMatchOperation insertMatchOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;
    final PgPool pgPool;

    @Override
    public Uni<Void> createMatch(CreateMatchInternalRequest request) {
        CreateMatchInternalRequest.validate(request);

        return Uni.createFrom().voidItem()
                .flatMap(validatedProject -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shard -> {
                    final var match = request.getMatch();
                    return createMatch(shard.shard(), match);
                });
    }

    Uni<Void> createMatch(final int shard, final MatchModel match) {
        return pgPool.withTransaction(sqlConnection -> insertMatchOperation.insertMatch(sqlConnection, shard, match)
                        .flatMap(voidItem -> {
                            final var matchmaker = match.getMatchmaker();
                            final var uuid = match.getUuid();
                            final var origin = MatchCreatedEventBodyModel.createEvent(matchmaker, uuid);
                            final var event = EventCreatedEventBodyModel.createEvent(origin);
                            final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                            return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest)
                                    .replaceWithVoid();
                        }))
                .invoke(voidItem -> matchmakerInMemoryCache.addMatch(match));
    }
}
