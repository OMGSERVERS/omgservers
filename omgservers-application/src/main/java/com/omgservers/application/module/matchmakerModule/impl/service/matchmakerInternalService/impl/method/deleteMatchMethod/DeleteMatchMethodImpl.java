package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.EventCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchOperation.DeleteMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteMatchInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteMatchInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchMethodImpl implements DeleteMatchMethod {

    final InternalModule internalModule;

    final DeleteMatchOperation deleteMatchOperation;
    final CheckShardOperation checkShardOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;
    final PgPool pgPool;

    @Override
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchInternalRequest request) {
        DeleteMatchInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmaker = request.getMatchmaker();
                    final var uuid = request.getUuid();
                    return deleteMatch(shard.shard(), matchmaker, uuid);
                })
                .map(DeleteMatchInternalResponse::new);
    }

    Uni<Boolean> deleteMatch(final int shard,
                             final UUID matchmaker,
                             final UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> deleteMatchOperation.deleteMatch(sqlConnection, shard, uuid)
                        .call(deleted -> {
                            if (deleted) {
                                final var origin = MatchDeletedEventBodyModel.createEvent(matchmaker, uuid);
                                final var event = EventCreatedEventBodyModel.createEvent(origin);
                                final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, event);
                                return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                            } else {
                                return Uni.createFrom().voidItem();
                            }
                        }))
                .invoke(deleted -> matchmakerInMemoryCache.removeMatch(uuid));
    }
}
