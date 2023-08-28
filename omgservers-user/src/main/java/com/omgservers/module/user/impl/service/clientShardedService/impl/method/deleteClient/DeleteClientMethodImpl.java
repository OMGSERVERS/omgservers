package com.omgservers.module.user.impl.service.clientShardedService.impl.method.deleteClient;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.user.DeleteClientShardResponse;
import com.omgservers.dto.user.DeleteClientShardedRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.deleteClient.DeleteClientOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteClientMethodImpl implements DeleteClientMethod {

    final InternalModule internalModule;

    final DeleteClientOperation deleteClientOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteClientShardResponse> deleteClient(final DeleteClientShardedRequest request) {
        DeleteClientShardedRequest.validate(request);

        final var clientId = request.getClientId();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        ((sqlConnection, shardModel) -> deleteClientOperation
                                .deleteClient(sqlConnection, shardModel.shard(), clientId)),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create("Client was deleted, " +
                                        "clientId=" + clientId);
                            } else {
                                return null;
                            }
                        }))
                .map(ChangeWithLogResponse::getResult)
                .map(DeleteClientShardResponse::new);
    }
}
