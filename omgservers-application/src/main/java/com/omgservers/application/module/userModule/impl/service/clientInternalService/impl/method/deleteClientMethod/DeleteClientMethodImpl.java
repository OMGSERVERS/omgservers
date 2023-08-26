package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod;

import com.omgservers.application.module.userModule.impl.operation.deleteClientOperation.DeleteClientOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.userModule.DeleteClientShardRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
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
    public Uni<DeleteClientInternalResponse> deleteClient(final DeleteClientShardRequest request) {
        DeleteClientShardRequest.validate(request);

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
                .map(DeleteClientInternalResponse::new);
    }
}
