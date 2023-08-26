package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchMethod;

import com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchOperation.DeleteMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.matchmakerModule.DeleteMatchShardRequest;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalResponse;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchMethodImpl implements DeleteMatchMethod {

    final InternalModule internalModule;

    final DeleteMatchOperation deleteMatchOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchShardRequest request) {
        DeleteMatchShardRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> deleteMatchOperation
                                .deleteMatch(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create("Matchmaker was deleted, matchmakerId=" + id);
                            } else {
                                return null;
                            }
                        },
                        deleted -> {
                            if (deleted) {
                                return new MatchDeletedEventBodyModel(matchmakerId, id);
                            } else {
                                return null;
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .invoke(deleted -> matchmakerInMemoryCache.removeMatch(id))
                .map(DeleteMatchInternalResponse::new);
    }
}
