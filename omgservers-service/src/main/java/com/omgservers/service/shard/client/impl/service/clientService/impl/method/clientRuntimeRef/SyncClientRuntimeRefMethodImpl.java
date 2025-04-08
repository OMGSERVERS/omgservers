package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.client.clientRuntimeRef.SyncClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.SyncClientRuntimeRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.client.impl.operation.client.VerifyClientExistsOperation;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.UpsertClientRuntimeRefOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncClientRuntimeRefMethodImpl implements SyncClientRuntimeRefMethod {

    final UpsertClientRuntimeRefOperation upsertClientRuntimeRefOperation;
    final VerifyClientExistsOperation verifyClientExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncClientRuntimeRefResponse> execute(final ShardModel shardModel,
                                                     final SyncClientRuntimeRefRequest request) {
        log.trace("{}", request);

        final var clientRuntimeRef = request.getClientRuntimeRef();
        final var clientId = clientRuntimeRef.getClientId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> verifyClientExistsOperation
                                .hasClient(sqlConnection, shardModel.shard(), clientId)
                                .flatMap(has -> {
                                    if (has) {
                                        return upsertClientRuntimeRefOperation.upsertClientRuntimeRef(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                clientRuntimeRef);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "client does not exist or was deleted, id=" + clientId);
                                    }
                                })
                )
                .map(ChangeContext::getResult)
                .map(SyncClientRuntimeRefResponse::new);
    }
}
