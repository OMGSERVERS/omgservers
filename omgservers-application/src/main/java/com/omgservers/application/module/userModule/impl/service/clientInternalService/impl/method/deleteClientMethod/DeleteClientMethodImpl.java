package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.deleteClientOperation.DeleteClientOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.DeleteClientInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteClientInternalResponse> deleteClient(final DeleteClientInternalRequest request) {
        DeleteClientInternalRequest.validate(request);

        final var clientId = request.getClientId();
        return changeOperation.changeWithLog(request,
                        ((sqlConnection, shardModel) -> deleteClientOperation
                                .deleteClient(sqlConnection, shardModel.shard(), clientId)),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create("Client was deleted, " +
                                        "clientId=" + clientId);
                            } else {
                                return null;
                            }
                        })
                .map(DeleteClientInternalResponse::new);
    }
}
