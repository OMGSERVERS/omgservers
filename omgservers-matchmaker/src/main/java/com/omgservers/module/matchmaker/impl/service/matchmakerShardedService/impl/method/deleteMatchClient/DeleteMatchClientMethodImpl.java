package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.deleteMatchClient;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.matchmaker.DeleteMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.DeleteMatchClientShardedResponse;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.deleteMatchClient.DeleteMatchClientOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteMatchClientMethodImpl implements DeleteMatchClientMethod {

    final InternalModule internalModule;

    final DeleteMatchClientOperation deleteMatchClientOperation;
    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteMatchClientShardedResponse> deleteMatchClient(DeleteMatchClientShardedRequest request) {
        DeleteMatchClientShardedRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> deleteMatchClientOperation
                                .deleteMatchClient(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Match client was deleted, " +
                                        "matchmakerId=%d, id=%d", matchmakerId, id));
                            } else {
                                return null;
                            }

                        }
                ))
                .map(ChangeWithLogResponse::getResult)
                .map(DeleteMatchClientShardedResponse::new);
    }
}
