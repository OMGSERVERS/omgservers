package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchMethod;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchOperation.DeleteMatchOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.base.impl.operation.changeOperation.ChangeOperation;
import com.omgservers.dto.matchmakerModule.DeleteMatchInternalRequest;
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
    final ChangeOperation changeOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteMatchInternalResponse> deleteMatch(DeleteMatchInternalRequest request) {
        DeleteMatchInternalRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return changeOperation.changeWithEvent(request,
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
                )
                .invoke(deleted -> matchmakerInMemoryCache.removeMatch(id))
                .map(DeleteMatchInternalResponse::new);
    }
}
