package com.omgservers.service.shard.match.impl.service.matchService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.match.SyncMatchRequest;
import com.omgservers.schema.shard.match.SyncMatchResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.match.impl.operation.match.UpsertMatchOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchMethodImpl implements SyncMatchMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchOperation matchOperation;

    @Override
    public Uni<SyncMatchResponse> execute(final ShardModel shardModel,
                                          final SyncMatchRequest request) {
        log.trace("{}", request);

        final var match = request.getMatch();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> matchOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                match))
                .map(ChangeContext::getResult)
                .map(SyncMatchResponse::new);
    }
}
