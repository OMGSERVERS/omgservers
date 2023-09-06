package com.omgservers.module.user.impl.service.clientService.impl.method.syncClient;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.SyncClientShardedRequest;
import com.omgservers.dto.user.SyncClientShardedResponse;
import com.omgservers.module.user.impl.operation.upsertClient.UpsertClientOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncClientMethodImpl implements SyncClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertClientOperation upsertClientOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncClientShardedResponse> syncClient(final SyncClientShardedRequest request) {
        SyncClientShardedRequest.validate(request);

        final var userId = request.getUserId();
        final var client = request.getClient();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertClientOperation.upsertClient(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                userId,
                                client)
                ))
                .map(ChangeContext::getResult)
                .map(SyncClientShardedResponse::new);
    }
}
