package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteRequestMethod;

import com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation.DeleteRequestOperation;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.MatchmakerInMemoryCache;
import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.matchmakerModule.DeleteRequestRoutedRequest;
import com.omgservers.dto.matchmakerModule.DeleteRequestInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRequestMethodImpl implements DeleteRequestMethod {

    final InternalModule internalModule;

    final DeleteRequestOperation deleteRequestOperation;

    final MatchmakerInMemoryCache matchmakerInMemoryCache;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteRequestInternalResponse> deleteRequest(DeleteRequestRoutedRequest request) {
        DeleteRequestRoutedRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        final var changeWithLogRequest = new ChangeWithLogRequest(request,
                (sqlConnection, shardModel) -> deleteRequestOperation
                        .deleteRequest(sqlConnection, shardModel.shard(), id),
                deleted -> {
                    if (deleted) {
                        return logModelFactory.create(String.format("Request was deleted, " +
                                "matchmakerId=%d, id=%d", matchmakerId, id));
                    } else {
                        return null;
                    }
                });
        return internalModule.getChangeService().changeWithLog(changeWithLogRequest)
                .map(ChangeWithLogResponse::getResult)
                .invoke(deleted -> matchmakerInMemoryCache.removeRequest(id))
                .map(DeleteRequestInternalResponse::new);
    }
}
