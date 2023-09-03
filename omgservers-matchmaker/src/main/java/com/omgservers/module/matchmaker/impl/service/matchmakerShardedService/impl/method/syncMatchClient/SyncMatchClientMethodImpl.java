package com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.impl.method.syncMatchClient;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.SyncMatchClientShardedResponse;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchClientMethodImpl implements SyncMatchClientMethod {

    final InternalModule internalModule;

    final UpsertMatchClientOperation upsertMatchClientOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncMatchClientShardedResponse> syncMatchClient(SyncMatchClientShardedRequest request) {
        SyncMatchClientShardedRequest.validate(request);

        final var matchClient = request.getMatchClient();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> upsertMatchClientOperation
                                .upsertMatchClient(sqlConnection, shardModel.shard(), matchClient),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Match client was created, matchClient=" + matchClient);
                            } else {
                                return null;
                            }
                        }
                ))
                .map(ChangeWithLogResponse::getResult)
                .map(SyncMatchClientShardedResponse::new);
    }
}
