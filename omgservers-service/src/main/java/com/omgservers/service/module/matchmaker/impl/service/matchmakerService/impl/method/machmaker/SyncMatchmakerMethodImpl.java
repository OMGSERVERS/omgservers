package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmaker.UpsertMatchmakerOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncMatchmakerResponse> execute(SyncMatchmakerRequest request) {
        log.trace("{}", request);

        final var matchmaker = request.getMatchmaker();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                                upsertMatchmakerOperation.execute(context,
                                        sqlConnection,
                                        shardModel.shard(),
                                        matchmaker))
                        .map(ChangeContext::getResult))
                .map(SyncMatchmakerResponse::new);
    }
}
