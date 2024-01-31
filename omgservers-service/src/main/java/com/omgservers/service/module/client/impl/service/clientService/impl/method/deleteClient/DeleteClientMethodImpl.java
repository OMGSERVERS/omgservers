package com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClient;

import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.service.module.client.impl.operation.deleteClient.DeleteClientOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteClientMethodImpl implements DeleteClientMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteClientOperation deleteClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteClientResponse> deleteClient(final DeleteClientRequest request) {
        log.debug("Delete client, request={}", request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteClientOperation
                                        .deleteClient(changeContext, sqlConnection, shardModel.shard(), id))
                        .map(ChangeContext::getResult))
                .map(DeleteClientResponse::new);
    }
}
