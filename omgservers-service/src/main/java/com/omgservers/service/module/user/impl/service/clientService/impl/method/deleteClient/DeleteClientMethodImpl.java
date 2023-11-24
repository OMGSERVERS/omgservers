package com.omgservers.service.module.user.impl.service.clientService.impl.method.deleteClient;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.dto.user.DeleteClientRequest;
import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.service.module.user.impl.operation.deleteClient.DeleteClientOperation;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
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

    @Override
    public Uni<DeleteClientResponse> deleteClient(final DeleteClientRequest request) {
        log.debug("Delete client, request={}", request);

        final var userId = request.getUserId();
        final var clientId = request.getClientId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteClientOperation.deleteClient(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                userId,
                                clientId)))
                .map(ChangeContext::getResult)
                .map(DeleteClientResponse::new);
    }
}
