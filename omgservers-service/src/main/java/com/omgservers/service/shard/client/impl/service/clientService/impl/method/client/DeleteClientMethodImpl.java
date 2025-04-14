package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.client.DeleteClientRequest;
import com.omgservers.schema.shard.client.client.DeleteClientResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.client.impl.operation.client.DeleteClientOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<DeleteClientResponse> execute(final ShardModel shardModel,
                                             final DeleteClientRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteClientOperation
                                .deleteClient(changeContext, sqlConnection, shardModel.shard(), id))
                .map(ChangeContext::getResult)
                .map(DeleteClientResponse::new);
    }
}
