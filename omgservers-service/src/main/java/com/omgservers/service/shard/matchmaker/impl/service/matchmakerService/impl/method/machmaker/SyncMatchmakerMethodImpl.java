package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.UpsertMatchmakerOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncMatchmakerMethodImpl implements SyncMatchmakerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertMatchmakerOperation upsertMatchmakerOperation;

    @Override
    public Uni<SyncMatchmakerResponse> execute(final ShardModel shardModel,
                                               final SyncMatchmakerRequest request) {
        log.debug("{}", request);

        final var matchmaker = request.getMatchmaker();
        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        upsertMatchmakerOperation.execute(context,
                                sqlConnection,
                                shardModel.slot(),
                                matchmaker))
                .map(ChangeContext::getResult)
                .map(SyncMatchmakerResponse::new);
    }
}
