package com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClientRuntimeRef;

import com.omgservers.model.dto.client.DeleteClientRuntimeRefRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefResponse;
import com.omgservers.service.module.client.impl.operation.clientRuntimeRef.deleteClientRuntimeRef.DeleteClientRuntimeRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
        log.debug("Delete client runtime ref, request={}", request);

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
