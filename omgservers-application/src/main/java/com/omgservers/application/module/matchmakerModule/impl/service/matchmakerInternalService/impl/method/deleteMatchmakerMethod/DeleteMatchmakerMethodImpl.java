package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchmakerMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.MatchmakerDeletedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchmakerOperation.DeleteMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.DeleteMatchmakerInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.DeleteMatchmakerInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchmakerMethodImpl implements DeleteMatchmakerMethod {

    final InternalModule internalModule;

    final DeleteMatchmakerOperation deleteMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteMatchmakerInternalResponse> deleteMatchmaker(DeleteMatchmakerInternalRequest request) {
        DeleteMatchmakerInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return deleteMatchmaker(shard.shard(), id);
                })
                .map(DeleteMatchmakerInternalResponse::new);
    }

    Uni<Boolean> deleteMatchmaker(final int shard,
                                  final Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteMatchmakerOperation
                .deleteMatchmaker(sqlConnection, shard, id)
                .call(deleted -> {
                    if (deleted) {
                        final var eventBody = new MatchmakerDeletedEventBodyModel(id);
                        final var insertEventInternalRequest = new InsertEventHelpRequest(sqlConnection, eventBody);
                        return internalModule.getEventHelpService().insertEvent(insertEventInternalRequest);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                })
                .call(deleted -> {
                    if (deleted) {
                        final var syncLog = logModelFactory.create("Matchmaker was deleted, matchmakerId=" + id);
                        final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                        return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                }));
    }
}
