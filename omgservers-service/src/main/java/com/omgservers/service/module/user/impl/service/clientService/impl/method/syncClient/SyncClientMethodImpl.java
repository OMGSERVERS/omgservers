package com.omgservers.service.module.user.impl.service.clientService.impl.method.syncClient;

import com.omgservers.model.dto.user.SyncClientRequest;
import com.omgservers.model.dto.user.SyncClientResponse;
import com.omgservers.service.module.user.impl.operation.upsertClient.UpsertClientOperation;
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
class SyncClientMethodImpl implements SyncClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertClientOperation upsertClientOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncClientResponse> syncClient(final SyncClientRequest request) {
        log.debug("Sync client, request={}", request);

        final var client = request.getClient();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertClientOperation.upsertClient(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                client)
                ))
                .map(ChangeContext::getResult)
                .map(SyncClientResponse::new);
    }
}
