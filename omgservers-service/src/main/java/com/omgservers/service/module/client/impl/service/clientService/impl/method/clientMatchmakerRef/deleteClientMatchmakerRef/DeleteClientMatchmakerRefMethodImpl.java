package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.deleteClientMatchmakerRef;

import com.omgservers.schema.module.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.DeleteClientMatchmakerRefResponse;
import com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.deleteClientMatchmakerRef.DeleteClientMatchmakerRefOperation;
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
class DeleteClientMatchmakerRefMethodImpl implements DeleteClientMatchmakerRefMethod {

    final DeleteClientMatchmakerRefOperation deleteClientMatchmakerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(DeleteClientMatchmakerRefRequest request) {
        log.trace("{}", request);

        final var clientId = request.getClientId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteClientMatchmakerRefOperation
                                        .deleteClientMatchmakerRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                clientId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteClientMatchmakerRefResponse::new);
    }
}
