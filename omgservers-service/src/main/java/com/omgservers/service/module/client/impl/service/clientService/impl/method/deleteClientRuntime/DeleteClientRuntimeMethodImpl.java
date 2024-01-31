package com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClientRuntime;

import com.omgservers.model.dto.client.DeleteClientRuntimeRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeResponse;
import com.omgservers.service.module.client.impl.operation.deleteClientRuntime.DeleteClientRuntimeOperation;
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
class DeleteClientRuntimeMethodImpl implements DeleteClientRuntimeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteClientRuntimeOperation deleteClientRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteClientRuntimeResponse> deleteClientRuntime(DeleteClientRuntimeRequest request) {
        log.debug("Delete client runtime, request={}", request);

        final var clientId = request.getClientId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteClientRuntimeOperation.deleteClientRuntime(changeContext, sqlConnection,
                                                shardModel.shard(), clientId, id))
                        .map(ChangeContext::getResult))
                .map(DeleteClientRuntimeResponse::new);
    }
}
