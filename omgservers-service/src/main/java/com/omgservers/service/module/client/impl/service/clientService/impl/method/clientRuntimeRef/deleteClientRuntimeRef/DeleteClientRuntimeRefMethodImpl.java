package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.deleteClientRuntimeRef;

import com.omgservers.schema.module.client.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.module.client.DeleteClientRuntimeRefResponse;
import com.omgservers.service.module.client.impl.operation.clientRuntimeRef.deleteClientRuntimeRef.DeleteClientRuntimeRefOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteClientRuntimeRefMethodImpl implements DeleteClientRuntimeRefMethod {

    final DeleteClientRuntimeRefOperation deleteClientRuntimeRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(DeleteClientRuntimeRefRequest request) {
        log.trace("{}", request);

        final var clientId = request.getClientId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteClientRuntimeRefOperation
                                        .deleteClientRuntimeRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                clientId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteClientRuntimeRefResponse::new);
    }
}
