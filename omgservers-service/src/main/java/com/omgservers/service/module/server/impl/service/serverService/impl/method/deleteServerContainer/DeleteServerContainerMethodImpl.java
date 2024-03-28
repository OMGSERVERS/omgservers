package com.omgservers.service.module.server.impl.service.serverService.impl.method.deleteServerContainer;

import com.omgservers.model.dto.server.DeleteServerContainerRequest;
import com.omgservers.model.dto.server.DeleteServerContainerResponse;
import com.omgservers.service.module.server.impl.operation.deleteServerContainer.DeleteServerContainerOperation;
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
class DeleteServerContainerMethodImpl implements DeleteServerContainerMethod {

    final DeleteServerContainerOperation deleteServerContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteServerContainerResponse> deleteServerContainer(DeleteServerContainerRequest request) {
        log.debug("Delete server container, request={}", request);

        final var serverId = request.getServerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteServerContainerOperation
                                        .deleteServerContainer(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                serverId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteServerContainerResponse::new);
    }
}
